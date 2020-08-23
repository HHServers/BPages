package io.github.hhservers.bpages.commands;

import io.github.hhservers.bpages.BPages;
import io.github.hhservers.bpages.util.PageBuilder;
import io.github.hhservers.bpages.util.PageObject;
import lombok.SneakyThrows;
import org.apache.commons.lang3.text.WordUtils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.ArrayList;
import java.util.List;

public class Reload implements CommandExecutor {
    @SneakyThrows
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        BPages.getInstance().reloadConfig();
        BPages.pageMap.clear();
        new PageBuilder().buildPage();
        src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&l&8[&r&dB&aPages&r&l&8]&r Config reloaded"));
        return CommandResult.success();
    }

    public static CommandSpec build(){
       return CommandSpec.builder()
                .permission("bpages.admin.reload")
                .description(Text.of("Reload command"))
                .executor(new Reload())
                .build();
    }
}
