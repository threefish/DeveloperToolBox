package com.gitee.threefish.idea.spring.gotosymbol;

import com.gitee.threefish.idea.spring.toolwindow.navigation.SpringRequestMappingNavigationItem;
import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.spring.web.mvc.mapping.UrlMapping;
import com.intellij.spring.web.mvc.model.mappings.UrlMappingPsiBasedElement;
import com.intellij.spring.web.mvc.services.SpringMvcService;
import com.intellij.xml.util.PsiElementPointer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2020/6/10
 */
public class AtMappingContributor implements ChooseByNameContributor, DumbAware {

    SpringMvcService springMvcService = SpringMvcService.getInstance();
    SpringRequestMappingNavigationItem[] alls;
    String[] allNames;

    @NotNull
    @Override
    public String[] getNames(Project project, boolean includeNonProjectItems) {
        if (Objects.isNull(alls)) {
            alls = this.getAll(project);
        }
        if (Objects.isNull(allNames)) {
            List<String> list = new ArrayList();
            for (SpringRequestMappingNavigationItem psiElement : alls) {
                list.add(psiElement.getText());
            }
            allNames = list.toArray(new String[0]);
        }
        return allNames;
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
        List<SpringRequestMappingNavigationItem> atMappingNavigationItems = new ArrayList<>();
        for (SpringRequestMappingNavigationItem item : alls) {
            if (matche(item.getText(), pattern)) {
                atMappingNavigationItems.add(item);
            }
        }
        return atMappingNavigationItems.toArray(new SpringRequestMappingNavigationItem[0]);
    }

    public boolean matche(String url, String pattern) {
        return url.toLowerCase().indexOf(pattern.toLowerCase()) > -1;
    }


    private SpringRequestMappingNavigationItem[] getAll(Project project) {
        Module[] modules = ModuleManager.getInstance(project).getModules();
        List<PsiElement> psiElements = new ArrayList<>();
        for (Module module : modules) {
            Set<UrlMapping<?>> urlMappings = springMvcService.getUrlMappings(module);
            urlMappings.forEach(urlMapping -> {
                if (urlMapping instanceof UrlMappingPsiBasedElement) {
                    UrlMappingPsiBasedElement urlMappingPsiBasedElement = (UrlMappingPsiBasedElement) urlMapping;
                    PsiElementPointer definition = urlMappingPsiBasedElement.getDefinition();
                    PsiElement navigationElement = definition.getPsiElement();
                    SpringRequestMappingNavigationItem springRequestMappingNavigationItem = new SpringRequestMappingNavigationItem(navigationElement, urlMapping.getURL(),urlMappingPsiBasedElement.getMethod());
                    psiElements.add(springRequestMappingNavigationItem);
                }
            });
        }
        return psiElements.toArray(new SpringRequestMappingNavigationItem[0]);
    }

}
