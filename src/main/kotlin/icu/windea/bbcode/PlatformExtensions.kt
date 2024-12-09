@file:Suppress("unused", "NOTHING_TO_INLINE")

package icu.windea.bbcode

import com.google.common.util.concurrent.*
import com.intellij.openapi.progress.*
import com.intellij.openapi.project.*
import com.intellij.openapi.vfs.*
import com.intellij.psi.*
import java.util.concurrent.*

inline fun <T> cancelable(block: () -> T): T {
    try {
        return block()
    } catch (e: ExecutionException) {
        val cause = e.cause
        if (cause is ProcessCanceledException) throw cause
        throw cause ?: e
    } catch (e: UncheckedExecutionException) {
        val cause = e.cause
        if (cause is ProcessCanceledException) throw cause
        throw cause ?: e
    } catch (e: ProcessCanceledException) {
        throw e
    }
}

inline fun <T> cancelable(defaultValueOnException: (Throwable) -> T, block: () -> T): T {
    try {
        return block()
    } catch (e: ExecutionException) {
        val cause = e.cause
        if (cause is ProcessCanceledException) throw cause
        return defaultValueOnException(cause ?: e)
    } catch (e: UncheckedExecutionException) {
        val cause = e.cause
        if (cause is ProcessCanceledException) throw cause
        return defaultValueOnException(cause ?: e)
    } catch (e: ProcessCanceledException) {
        throw e
    }
}

inline fun <R> runCatchingCancelable(block: () -> R): Result<R> {
    return runCatching(block).onFailure { if (it is ProcessCanceledException) throw it }
}

inline fun <T, R> T.runCatchingCancelable(block: T.() -> R): Result<R> {
    return runCatching(block).onFailure { if (it is ProcessCanceledException) throw it }
}

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

/** 将VirtualFile转化为指定类型的PsiFile。 */
inline fun VirtualFile.toPsiFile(project: Project): PsiFile? {
    return PsiManager.getInstance(project).findFile(this)
}

/** 将VirtualFile转化为指定类型的PsiDirectory。 */
inline fun VirtualFile.toPsiDirectory(project: Project): PsiDirectory? {
    return PsiManager.getInstance(project).findDirectory(this)
}

/** 将VirtualFile转化为指定类型的PsiFile或者PsiDirectory。 */
inline fun VirtualFile.toPsiFileSystemItem(project: Project): PsiFileSystemItem? {
    return if (this.isFile) PsiManager.getInstance(project).findFile(this) else PsiManager.getInstance(project).findDirectory(this)
}
