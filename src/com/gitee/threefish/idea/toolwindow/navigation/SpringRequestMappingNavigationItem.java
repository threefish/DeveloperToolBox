package com.gitee.threefish.idea.toolwindow.navigation;

import com.intellij.psi.PsiElement;
import com.intellij.util.xml.model.gotosymbol.GoToSymbolProvider;
import icons.SpringApiIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2020/6/3
 */
public class SpringRequestMappingNavigationItem extends GoToSymbolProvider.BaseNavigationItem {

    private final String text;

    public SpringRequestMappingNavigationItem(@NotNull PsiElement psiElement, @NotNull String text) {
        super(psiElement, text, SpringApiIcons.RequestMapping);
        this.text = text;
    }

    public SpringRequestMappingNavigationItem(@NotNull PsiElement psiElement, @NotNull String text, @Nullable Icon icon) {
        super(psiElement, text, SpringApiIcons.RequestMapping);
        this.text = text;
    }


    @Nullable
    @Override
    public String getText() {
        return this.text;
    }
}
