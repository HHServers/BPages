package io.github.hhservers.bpages.commands;

import io.github.hhservers.bpages.BPages;
import io.github.hhservers.bpages.util.PageObject;
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

import javax.swing.text.TextAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Base implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(args.hasAny(Text.of("pageID"))){
            String pageID = args.<String>getOne(Text.of("pageID")).get().toLowerCase();
            BPages.pageMap.get(pageID).sendTo(src);
        } else {
            List<Text> textList = new ArrayList<>();
            for(PageObject obj : BPages.getMainPluginConfig().getPageList()){
                String s = WordUtils.capitalize(obj.getCommandAlias());
                textList.add(TextSerializers.FORMATTING_CODE.deserialize("&l&8-&r&b"+s).toBuilder()
                .onHover(TextActions.showText(Text.of("Click me to open this page")))
                .onClick(TextActions.runCommand("/page "+obj.getCommandAlias()))
                .build());
            }
            PaginationList.builder()
                    .title(TextSerializers.FORMATTING_CODE.deserialize("&l&8[&r&bPages&r&l&8]&r"))
                    .contents(textList)
                    .padding(TextSerializers.FORMATTING_CODE.deserialize("&l&8="))
                    .sendTo(src);
        }
        return CommandResult.success();
    }

    public static CommandSpec build(){
       return CommandSpec.builder()
                .arguments(GenericArguments.optional(GenericArguments.string(Text.of("pageID"))))
                .permission("bpages.user.base")
                .description(Text.of("Base command"))
                .executor(new Base())
                .build();
    }
}
