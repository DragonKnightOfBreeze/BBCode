@file:Suppress("unused")

package icu.windea.bbcode

import com.intellij.openapi.project.*
import com.intellij.psi.*

private object EmptyPointer : SmartPsiElementPointer<PsiElement> {
    override fun getElement() = null

    override fun getContainingFile() = null

    override fun getProject() = ProjectManager.getInstance().defaultProject

    override fun getVirtualFile() = null

    override fun getRange() = null

    override fun getPsiRange() = null
}

fun <T : PsiElement> emptyPointer(): SmartPsiElementPointer<T> = EmptyPointer.cast()

fun SmartPsiElementPointer<*>.isEmpty() = this === EmptyPointer

fun <E : PsiElement> E.createPointer(project: Project = this.project): SmartPsiElementPointer<E> {
    return try {
        SmartPointerManager.getInstance(project).createSmartPsiElementPointer(this)
    } catch (e: IllegalArgumentException) {
        //Element from alien project - use empty pointer
        emptyPointer()
    }
}

fun <E : PsiElement> E.createPointer(file: PsiFile?, project: Project = this.project): SmartPsiElementPointer<E> {
    return try {
        SmartPointerManager.getInstance(project).createSmartPsiElementPointer(this, file)
    } catch (e: IllegalArgumentException) {
        //Element from alien project - use empty pointer
        emptyPointer()
    }
}
