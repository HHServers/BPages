package io.github.hhservers.bpages.config;

import io.github.hhservers.bpages.util.PageObject;
import lombok.Data;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.Arrays;
import java.util.List;

@ConfigSerializable @Data
public class MainPluginConfig {

    @Setting(value = "pageList")
    private List<PageObject> pageList = Arrays.asList(new PageObject());

}
