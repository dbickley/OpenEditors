package com.deepnoodle.openeditors.ui;

import static com.deepnoodle.openeditors.utils.ListUtils.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.deepnoodle.openeditors.logging.LogWrapper;
import com.deepnoodle.openeditors.models.EditorModel;
import com.deepnoodle.openeditors.ui.EditorTableView.IEditorTableViewPresenter;
import com.deepnoodle.openeditors.utils.MapUtils;
import com.deepnoodle.openeditors.utils.PathUtils;

class EditorViewLabelProvider extends DelegatingStyledCellLabelProvider {

	private static LogWrapper log = new LogWrapper( EditorViewLabelProvider.class );

	public EditorViewLabelProvider(IEditorTableViewPresenter presenter) {
		super( new EditorStyledLabelProvider( presenter ) );
	}

	@Override
	public String getToolTipText(Object element) {
		EditorModel editor = ( (EditorModel) element );
		return editor.getFilePath();
	}

	@Override
	public boolean useNativeToolTip(Object object) {
		return true;
	}

	@Override
	public EditorStyledLabelProvider getStyledStringProvider() {
		return (EditorStyledLabelProvider) super.getStyledStringProvider();
	}

	public void updateLabels(List<EditorModel> editors) {
		getStyledStringProvider().updateLabels( editors );
	}

	private static class EditorStyledLabelProvider extends LabelProvider implements IStyledLabelProvider {

		private static final RGB PINNED_COLOR = hexToRGB( "#E80C2E" );

		private Map<EditorModel, EditorLabelData> editorToLabelDataMap = new HashMap<>();

		IEditorTableViewPresenter presenter;

		public EditorStyledLabelProvider(IEditorTableViewPresenter presenter) {
			this.presenter = presenter;
		}

		@Override
		public Image getImage(Object obj) {
			Image image = null;
			EditorModel editor = ( (EditorModel) obj );
			if( editor.getReference() != null ) {
				image = editor.getReference().getTitleImage();
			}

			//Default to file image if none found
			if( image == null ) {
				image = PlatformUI.getWorkbench().getSharedImages().getImage( ISharedImages.IMG_OBJ_FILE );
			}
			return image;
		}

		@Override
		public StyledString getStyledText(Object element) {
		    EditorModel editor = (EditorModel) element;
			EditorLabelData editorLabelData = editorToLabelDataMap.get( editor );

			if( editorLabelData == null ) {
				log.warn( "getText: No EditorLabelData found for editor '" + editor + "'. Updating the labels." );
				List<EditorModel> editors = presenter.getSortedEditors();
				updateLabels( editors );
				editorLabelData = editorToLabelDataMap.get( editor );
			}

			try {
				editorLabelData = editorToLabelDataMap.get( editor );
				StyledString str = new StyledString();
				if( editorLabelData.folderLabel != null ) {
					str.append( editorLabelData.folderLabel + "/", StyledString.QUALIFIER_STYLER );
				}
				if( editorLabelData.fileLabel != null ) {
					// Add asterisk for dirty editors
					String fileLabel = ( editor.isDirty() ? "*" : "" ) + editorLabelData.fileLabel;

					List<Styler> fileLabelStylers = new ArrayList<>();
					// Give pinned editors another color
					if( editor.isPinned() ) {
						fileLabelStylers.add( getPinnedColorStyler() );
					}

					str.append( fileLabel, new CompositeStyler( fileLabelStylers ) );
				}
				return str;
			} catch( Exception e ) {
				log.error( e );
				return new StyledString( editor.getName() );
			}
		}

		private static Styler getPinnedColorStyler() {
			Styler styler = new Styler() {
				@Override
				public void applyStyles(TextStyle textStyle) {
					// Add or update the color in the registry.
					// This way, there is at most one instance, so a missing dispose is irrelevant.
					JFaceResources.getColorRegistry().put( "com.deepnoodle.openeditors.pinnedColor", PINNED_COLOR );
					textStyle.foreground =
					    JFaceResources.getColorRegistry().get( "com.deepnoodle.openeditors.pinnedColor" );
				}
			};
			return styler;
		}

		private static RGB hexToRGB(String hexColor) {
			int r = Integer.valueOf( hexColor.substring( 1, 3 ), 16 );
			int g = Integer.valueOf( hexColor.substring( 3, 5 ), 16 );
			int b = Integer.valueOf( hexColor.substring( 5, 7 ), 16 );
			return new RGB( r, g, b );
		}

		/**
		 * Updates the map of unique labels for the editors.
		 * In case some editors have the same filename,
		 * the parent directories will be included in the label
		 * and common parts are abbreviated using "...".
		 */
		public void updateLabels(List<EditorModel> editors) {
			editorToLabelDataMap.clear();
			// Group editors with same name
			HashMap<String, List<EditorModel>> fileNameToEditorsMap = new HashMap<>();
			for( EditorModel editor : editors ) {
				String fileName = editor.getName();
				MapUtils.putAppending( fileNameToEditorsMap, fileName, editor );
			}

			// Find unique label for all editors in the groups
			for( Entry<String, List<EditorModel>> entry : fileNameToEditorsMap.entrySet() ) {
				String fileName = entry.getKey();
				List<EditorModel> editorsWithSameName = entry.getValue();
				if( editorsWithSameName.size() == 1 ) {
					EditorLabelData editorLabelData = new EditorLabelData( null, fileName );
					EditorModel editor = editorsWithSameName.get( 0 );
					editorToLabelDataMap.put( editor, editorLabelData );
				} else {
					Map<EditorModel, IPath> editorToFolderMap = new HashMap<>();
					editorsWithSameName.forEach( (editor) -> {
						IPath file = new Path( editor.getFilePath() );
						IPath folder = PathUtils.getParentPath( file );
						editorToFolderMap.put( editor, folder );
					} );
					Map<EditorModel, IPath> editorToSuffixMap = uniqueFolderSuffixes( editorToFolderMap );
					Map<EditorModel, IPath> editorToPrefixMap = abbreviateFolderSuffixes( editorToSuffixMap );
					for( Entry<EditorModel, IPath> editorToPrefixEntry : editorToPrefixMap.entrySet() ) {
						IPath prefix = editorToPrefixEntry.getValue();
						EditorModel editor = editorToPrefixEntry.getKey();
						EditorLabelData editorLabelData = new EditorLabelData( prefix.toString(), fileName );
						editorToLabelDataMap.put( editor, editorLabelData );
					}
				}
			}
		}

		private static Map<EditorModel, IPath> uniqueFolderSuffixes(Map<EditorModel, IPath> editorToFolderMap) {
			Map<EditorModel, IPath> editorToSuffixMap = new HashMap<>();
			Collection<IPath> folderPaths = editorToFolderMap.values();
			for( Entry<EditorModel, IPath> entry : editorToFolderMap.entrySet() ) {
				IPath path = entry.getValue();
				List<IPath> otherPaths = filter( folderPaths, (it) -> it != path );
				String uniqueSuffix = PathUtils.findUniqueSuffix( path, otherPaths );
				editorToSuffixMap.put( entry.getKey(), new Path( uniqueSuffix ) );
			}
			return editorToSuffixMap;
		}

		private static Map<EditorModel, IPath> abbreviateFolderSuffixes(Map<EditorModel, IPath> editorToSuffixMap) {
			Map<EditorModel, IPath> uniquePrefixes = new HashMap<>();
			Collection<IPath> folderPaths = editorToSuffixMap.values();
			for( Entry<EditorModel, IPath> entry : editorToSuffixMap.entrySet() ) {
				IPath path = entry.getValue();
				List<IPath> otherPaths = filter( folderPaths, (it) -> it != path );
				String uniquePrefix = PathUtils.abbreviateUniqueSuffix( path, otherPaths );
				uniquePrefixes.put( entry.getKey(), new Path( uniquePrefix ) );
			}
			return uniquePrefixes;
		}
	}

	private static class EditorLabelData {
		private String folderLabel;
		private String fileLabel;

		public EditorLabelData(String folderLabel, String fileLabel) {
			this.folderLabel = folderLabel;
			this.fileLabel = fileLabel;
		}

		@Override
		public String toString() {
			return "EditorLabelData[folderLabel:" + getFolderLabel() + ", fileLabel:" + getFileLabel() + "]";
		}

		public String getFolderLabel() {
			return folderLabel;
		}

		public String getFileLabel() {
			return fileLabel;
		}
	}
}