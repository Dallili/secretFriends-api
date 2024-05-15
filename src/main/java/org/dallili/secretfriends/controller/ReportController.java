package org.dallili.secretfriends.controller;

import lombok.RequiredArgsConstructor;
import org.dallili.secretfriends.dto.GptDTO;
import org.dallili.secretfriends.dto.ReportDTO;
import org.dallili.secretfriends.service.EntryService;
import org.dallili.secretfriends.service.ReportService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("{entryID}/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;


}
