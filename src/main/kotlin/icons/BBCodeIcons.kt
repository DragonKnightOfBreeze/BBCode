package icons

import com.intellij.icons.*
import com.intellij.ui.*
import javax.swing.*

object BBCodeIcons {
    @JvmField
    val BBCode = loadIcon("/icons/bbcode.svg")
    @JvmField
    val Tag = AllIcons.Nodes.Tag

    @JvmStatic
    fun loadIcon(path: String): Icon {
        return IconManager.getInstance().getIcon(path, BBCodeIcons::class.java)
    }
}
