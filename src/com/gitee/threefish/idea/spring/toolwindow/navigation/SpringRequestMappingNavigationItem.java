package com.gitee.threefish.idea.spring.toolwindow.navigation;

import com.gitee.threefish.idea.util.Icons;
import com.intellij.psi.PsiElement;
import com.intellij.spring.web.mvc.jam.RequestMethod;
import com.intellij.util.xml.model.gotosymbol.GoToSymbolProvider;
import icons.SpringApiIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


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

    public SpringRequestMappingNavigationItem(@NotNull PsiElement psiElement, @NotNull String text, RequestMethod[] method) {
        super(psiElement, text, Icons.getMethodIcon(method));
        this.text = text;
    }

    @Nullable
    @Override
    public String getText() {
        return this.text;
    }
}
