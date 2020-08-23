package io.github.hhservers.bpages.config;

import io.github.hhservers.bpages.util.PageObject;
import lombok.Data;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.Arrays;
import java.util.List;

@ConfigSerializable @Data
public class MainPluginConfig {

    @Setting(value = "mainCommandAlias")
    private String commandAlias = "page";

    @Setting(value = "mainPagePadding")
    private String mainPagePadding = "&6=";

    @Setting(value = "mainPageTitle")
    private String mainPageTitle = "&l&8[&r&dB&aPages&l&8]&r";

    @Setting(value = "mainPagePrefix")
    private String mainPagePrefix = "&l&8-&r&b";

    @Setting(value = "mainLinesPerPage")
    private int mainLinesPerPage = 10;

    @Setting(value = "pageListNode")
    private MainPluginConfig.PageList pageListNode = new PageList();


    @ConfigSerializable @Data
    public static class PageList {

        @Setting(value = "pageList")
        private List<PageObject> pageList = Arrays.asList(new PageObject());

    }



}
