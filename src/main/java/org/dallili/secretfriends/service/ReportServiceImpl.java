package org.dallili.secretfriends.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.dallili.secretfriends.domain.Entry;
import org.dallili.secretfriends.domain.Report;
import org.dallili.secretfriends.dto.GptDTO;
import org.dallili.secretfriends.dto.ReportDTO;
import org.dallili.secretfriends.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportServiceImpl implements ReportService{

    @Value("${openai.model}")
    private String model;
    @Value("${openai.api.url}")
    private String apiUrl;
    private final RestTemplate restTemplate;

    private final ReportRepository reportRepository;
    @Override
    public void addReport(Entry entry) {

        String content = entry.getContent();

        List<GptDTO.Message> messages = new ArrayList<>();
        messages.add(GptDTO.Message.builder().role("user").content(content).build());
        messages.add(GptDTO.Message.builder().role("user").content("앞선 일기를 감정 분석한 후 작성자의 기분을 매우 긍정, 약간 긍정, 보통, 약간 부정, 매우 부정 다섯 개 중 하나로 분류해줘.  두번째로, 일기를 '<상황>에 대한 <감정>' 형식으로 요약해줘. 마지막으로, 일기를 감정분석해서 작성자의 기분을 나타내는 색상의 hex code 를 줘.").build());
        messages.add(GptDTO.Message.builder().role("user").content("응답 형식은 다음과 같아. sentiment: 매우 긍정 \n summary: 대학생활에 대한 즐거움 \n color: #000000").build());


        GptDTO.Request request = new GptDTO.Request(
                model,messages,1,300,1,2,2);

        GptDTO.Response gptResponse = restTemplate.postForObject(
                apiUrl
                , request
                , GptDTO.Response.class
        );

        String result =  gptResponse.getChoices().get(0).getMessage().getContent();

        String[] lines = result.split("\n");

        ReportDTO.Details response = ReportDTO.Details.builder()
                .sentiment(lines[0].substring("sentiment:".length()).trim())
                .summary(lines[1].substring("summary:".length()).trim())
                .color(lines[2].substring("color:".length()).trim())
                .build();

        Report report = response.toEntity(entry);

        reportRepository.save(report);
    }

    @Override
    public ReportDTO.Details findReport(Long entryID) {
        Report report = reportRepository.findReportByEntry_EntryID(entryID).orElseThrow(()->{
            throw new IllegalArgumentException(entryID + ": 해당 일기에 대한 레포트가 존재하지 않습니다.");
        });
        ReportDTO.Details response = report.toDto();
        return response;
    }

    @Override
    public List<ReportDTO.List> findReportList(Long memberID, Long diaryID) {
        List<Report> reports = reportRepository.findReportsByMemberAndDiary(memberID,diaryID);
        List<ReportDTO.List> reportList = new ArrayList<>();
        int index = 0;
        for(Report report:reports){
            reportList.add(ReportDTO.List.builder()
                            .index(index)
                            .entryID(report.getEntry().getEntryID())
                            .sentiment(report.getSentiment())
                            .summary(report.getSummary())
                            .color(report.getColor())
                            .date(report.getCreatedAt())
                    .build());
            index++;
        }
        return reportList;
    }
}
