package org.dallili.secretfriends.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.dto.PageDTO;
import org.dallili.secretfriends.service.PageService;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pages")
@Log4j2
@RequiredArgsConstructor
public class PageController {

    private final PageService pageService;

    @Operation(summary = "Pages POST", description = "일기 생성")
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,Long> pageAdd(@Valid @RequestBody PageDTO pageDTO, BindingResult bindingResult) throws BindException{

        log.info(pageDTO);
        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }

        Map<String,Long> result = new HashMap<>();

        Long pageId = pageService.addPage(pageDTO);
        result.put("pageID",pageId);

        return result;

    }
}
