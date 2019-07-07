
Open Editors is an Eclipse plugin. It adds a view that shows all open editors in a vertical list.

You can find more information on the [GitHub Pages Site](https://dbickley.github.io/OpenEditors/).

Published on the [Eclipse Marketplace](https://marketplace.eclipse.org/content/open-editors)
<br />Update Site: https://raw.githubusercontent.com/dbickley/OpenEditors/release/updatesite

[![Drag to your running Eclipse* workspace. *Requires Eclipse Marketplace Client](https://marketplace.eclipse.org/sites/all/themes/solstice/public/images/marketplace/btn-install.png)](http://marketplace.eclipse.org/marketplace-client-intro?mpc_install=4046826 "Drag to your running Eclipse workspace. (Requires Eclipse Marketplace Client)")


# Development Environment Setup

1) Clone the repository
1) Download and start Eclipse
1) Install the plugin Development Tools (PDE) if not done yet
    1) Help > Install New Software...
    1) Work with `download.eclipse.org/releases/...`
    1) Select 'General Purpose Tools' > 'Eclipse plugin Development Environment.'
1) Import the projects from the cloned repository
    1) File > Import... > Existing Projects Into Workspace
    1) Select the repository location and import all projects

1) Configure the Target Platform. It must contain all required plugins and should not contain to much bloat, such that the test environment starts up quickly.
    1) A Target Platform has been prepared in `com.deepnoodle.openeditors/config/target-platforms/openeditors.target`. Open this file and click 'Set as Active Target Platform' in the upper right corner.
    <br /><img src="web-assets/set-as-target-platform.png" width="400">

1) Run the plugin in a new Eclipse instance.
    1) A launch configuration has been prepared in `com.deepnoodle.openeditors/config/launch-configurations`. You can import it via File > Import... > Run/Debug > Launch Configuration.

# Conventions and Configurations

1) Use spaces instead tabs: Preferences > General > Editors > Text Editors > Insert spaces for tabs: true

1) Enable UTF-8 encoding: Preferences > General > Workspace > Text file encoding: UTF-8

1) You can find the code style configuration in `com.deepnoodle.openeditors/config/code-style`.
    1) Import the configuration file in Eclipse under Preferences > Java > Code Style > Formatter.

    1) Enable formatting on save using Save Actions: Preferences > Java > Editor > Save Actions > Format source code
    <br /><img src="web-assets/save-actions.png" width="400">

# Building the Update Site

The Eclipse Update Site for this plugin is hosted also on GitHub. You find it in the 'updatesite' folder. The folder in the 'master' branch is for alpha versions and pre-release testing, whereas the 'release' branch contains the released versions of the plugin that are considered stable.

The plugin project contains the code for the plugin. On the other hand, the feature project (ends with '.feature') contains information to bundle and deploy the plugin as an Eclipse Update Site.

1) Go to File > Export... > Deployable Feature
<br /><img src="web-assets/export-deployable-feature1.png" width="400">

1) Choose a target directory.

1) Under the 'Options' tab, make sure that 'Categorize repository' is checked
<br /><img src="web-assets/export-deployable-feature2.png" width="400">

1) Hit 'Finish'