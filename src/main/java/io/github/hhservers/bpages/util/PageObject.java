package io.github.hhservers.bpages.util;

import lombok.Data;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.Arrays;
import java.util.List;

@ConfigSerializable @Data
public class PageObject {

    @Setting("alias")
    private String commandAlias = "pagetest";

    @Setting("content")
    private List<String> content = Arrays.asList("Example content");

    @Setting("padding")
    private String padding = "&a=&d=";

    @Setting("title")
    private String title = "&aExample Title";

    @Setting("linesPerPage")
    private int linesPerPage = 10;

}
