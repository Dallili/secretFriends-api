package org.dallili.secretfriends.config;

import org.dallili.secretfriends.domain.Diary;
import org.dallili.secretfriends.dto.DiaryDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RootConfig {

    //ModelMapper를 스프링의 빈으로 설정
    @Bean
    public ModelMapper getMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        modelMapper.addMappings(diaryMapping);
        modelMapper.addMappings(diaryFieldMapping);
        return modelMapper;
    }

    PropertyMap<DiaryDTO, Diary> diaryMapping = new PropertyMap<DiaryDTO, Diary>() {
        @Override
        protected void configure() {
            map().getMember().setMemberID(source.getMemberID());
            map().getPartner().setMemberID(source.getPartnerID());
            map().getMember().setNickname(source.getMemberName());
            map().getPartner().setNickname(source.getPartnerName());
        }
    };

    PropertyMap<Diary,DiaryDTO> diaryFieldMapping = new PropertyMap<Diary, DiaryDTO>() {
        @Override
        protected void configure() {
            map().setMemberID(source.getMember().getMemberID());
            map().setPartnerID(source.getPartner().getMemberID());
            map().setMemberName(source.getMember().getNickname());
            map().setPartnerName(source.getPartner().getNickname());
        }
    };


}