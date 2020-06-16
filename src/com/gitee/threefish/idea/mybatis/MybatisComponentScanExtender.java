package com.gitee.threefish.idea.mybatis;

import com.intellij.openapi.module.Module;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.searches.AnnotatedElementsSearch;
import com.intellij.spring.model.custom.ComponentScanExtender;
import com.intellij.spring.model.jam.stereotype.CustomSpringComponent;
import com.intellij.spring.model.jam.stereotype.SpringStereotypeElement;
import com.intellij.util.Query;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2018/9/20
 * 解决在 Spring 中使用 @Mapper intellij 中会报 “Could not autowire. No beans of 'xxx' type found” 的错误
 */
public class MybatisComponentScanExtender extends ComponentScanExtender {

    public static final String MYBATIS_MAPPER_ANNOTATION = "org.apache.ibatis.annotations.Mapper";

    @NotNull
    @Override
    public Collection<? extends SpringStereotypeElement> getComponents(@NotNull GlobalSearchScope scope, @NotNull Module module) {
        List<SpringStereotypeElement> springStereotypeElements = new ArrayList<>();
        GlobalSearchScope searchScope = GlobalSearchScope.moduleWithDependenciesAndLibrariesScope(module);
        PsiClass psiFacade = JavaPsiFacade.getInstance(module.getProject()).findClass(MYBATIS_MAPPER_ANNOTATION, searchScope);
        if (psiFacade == null) {
            return springStereotypeElements;
        }
        Query<PsiClass> psiClassQuery = AnnotatedElementsSearch.searchPsiClasses(psiFacade, GlobalSearchScope.moduleScope(module));
        for (PsiClass psiClass : psiClassQuery.findAll()) {
            springStereotypeElements.add(new CustomSpringComponent(psiClass));
        }
        return springStereotypeElements;
    }
}
