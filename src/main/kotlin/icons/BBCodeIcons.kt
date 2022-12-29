package icons

import com.intellij.icons.AllIcons
import com.intellij.ui.IconManager
import javax.swing.Icon

object BBCodeIcons {
    @JvmField val BBCode = loadIcon("/icons/bbcode.svg")
    @JvmField val Tag = AllIcons.Nodes.Tag
    
    @JvmStatic
    fun loadIcon(path: String): Icon {
        return IconManager.getInstance().getIcon(path, BBCodeIcons::class.java)
    }
}