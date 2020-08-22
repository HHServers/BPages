package io.github.hhservers.bpages.util;

import io.github.hhservers.bpages.BPages;
import io.github.hhservers.bpages.config.MainPluginConfig;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.ArrayList;
import java.util.List;

public class PageBuilder {

    public void buildPage(){
        MainPluginConfig conf = BPages.getMainPluginConfig();
        List<PageObject> pageList = conf.getPageList();
        for(PageObject obj : pageList){
            List<Text> textList = new ArrayList<>();
            for(String s : obj.getContent()){
                textList.add(TextSerializers.FORMATTING_CODE.deserialize(s));
            }
            BPages.pageMap.put(obj.getCommandAlias().toLowerCase(), PaginationList.builder()
                    .contents(textList)
                    .padding(TextSerializers.FORMATTING_CODE.deserialize(obj.getPadding()))
                    .linesPerPage(obj.getLinesPerPage())
                    .title(TextSerializers.FORMATTING_CODE.deserialize(obj.getTitle()))
                    .build());
        }
    }

}
