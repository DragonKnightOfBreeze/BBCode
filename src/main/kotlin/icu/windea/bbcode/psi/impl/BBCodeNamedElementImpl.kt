package icu.windea.bbcode.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import icu.windea.bbcode.psi.BBCodeNamedElement

abstract class BBCodeNamedElementImpl(node:ASTNode):ASTWrapperPsiElement(node) , BBCodeNamedElement

