package com.wdcloud.lms.base.service;

import com.wdcloud.lms.base.dto.FileInfo;
import com.wdcloud.lms.config.OssClient;
import com.wdcloud.lms.core.base.dao.UserEPortfolioPageDao;
import com.wdcloud.lms.core.base.dao.UserEPortfolioPageFileDao;
import com.wdcloud.lms.core.base.dao.UserFileDao;
import com.wdcloud.lms.core.base.model.UserEPortfolioPage;
import com.wdcloud.lms.core.base.model.UserEPortfolioPageFile;
import com.wdcloud.lms.core.base.model.UserFile;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EPortfolioPageFileService {

    @Autowired
    private UserFileDao userFileDao;
    @Autowired
    private UserEPortfolioPageDao userEPortfolioPageDao;
    @Autowired
    private UserEPortfolioPageFileDao userEPortfolioPageFileDao;

    @Autowired
    private UserFileService userFileService;

    @Autowired
    private OssClient ossClient;

    public void add(Long ePortfolioPageId, List<String> fileIds){
        if(ListUtils.isNotEmpty(fileIds)){
            for(String fileId : fileIds){
                FileInfo fileInfo = userFileService.getFileInfo(fileId);
                UserFile userFile = userFileService.saveUserEPortfolioFile(fileInfo);

                UserEPortfolioPageFile userEPortfolioPageFile = UserEPortfolioPageFile.builder()
                        .ePortfolioPageId(ePortfolioPageId).userFileId(userFile.getId()).build();
                userEPortfolioPageFileDao.save(userEPortfolioPageFile);
            }
        }
    }

    public void copy(Long originPageId, Long newPageId){
        List<UserEPortfolioPageFile> ePortfolioPageFiles = userEPortfolioPageFileDao.getByEPortfolioPageId(originPageId);
        for(UserEPortfolioPageFile ePortfolioPageFile : ePortfolioPageFiles){
            UserEPortfolioPageFile userEPortfolioPageFile = UserEPortfolioPageFile.builder()
                    .ePortfolioPageId(newPageId).userFileId(ePortfolioPageFile.getUserFileId()).build();

            userEPortfolioPageFileDao.save(userEPortfolioPageFile);
        }
    }

    public void copy(List<Long> ePortfolioPageIds){
        for(Long ePortfolioPageId : ePortfolioPageIds){
            this.copy(ePortfolioPageId, null);
        }
    }

    /**
     * 删除页面，及页面中不再引用的文件。目前删除通过删除章节触发。
     * @param ePortfolioPageId
     * @param fileUrls
     */
    public void delete(Long ePortfolioPageId, List<String> fileUrls){
        if(ListUtils.isNotEmpty(fileUrls)){
            List<UserFile> userFiles = userFileDao.getByFileUrlIn(fileUrls);
            List<Long> userFileIds = userFiles.stream().map(UserFile::getId).collect(Collectors.toList());
            userEPortfolioPageFileDao.delete(ePortfolioPageId, userFileIds);

            deleteUserFileByEPortfolioPageFileNotIn(userFiles);
        }
    }

    public void deleteByEPortfolioPageId(Long ePortfolioPageId){
        List<UserEPortfolioPageFile> ePortfolioPageFiles = userEPortfolioPageFileDao.getByEPortfolioPageId(ePortfolioPageId);

        if(ListUtils.isNotEmpty(ePortfolioPageFiles)){
            List<Long> userFileIds = ePortfolioPageFiles.stream()
                    .map(UserEPortfolioPageFile::getUserFileId).collect(Collectors.toList());
            userEPortfolioPageFileDao.delete(ePortfolioPageId, userFileIds);

            List<UserFile> userFiles = userFileDao.gets(userFileIds);
            deleteUserFileByEPortfolioPageFileNotIn(userFiles);
        }
    }

    /**
     * 需要删除学档中的不被引用的文件。因为复制操作，一个文件被多个电子学档页面引用。
     * @param ePortfolioColumnId
     */
    public void deleteByEPortfolioColumnId(Long ePortfolioColumnId) {
        List<UserEPortfolioPage> ePortfolioPages = userEPortfolioPageDao.getByEPortfolioColumnId(ePortfolioColumnId);
        for(UserEPortfolioPage userEPortfolioPage : ePortfolioPages){
            this.deleteByEPortfolioPageId(userEPortfolioPage.getId());
        }
    }

    /**
     * 需要删除学档中的不被引用的文件。因为复制操作，一个文件被多个电子学档页面引用。
     * @param ePortfolioId
     */
    public void deleteByEPortfolioId(Long ePortfolioId) {
        List<Long> ePortfolioPageIds = userEPortfolioPageDao.getByEPortfolioId(ePortfolioId);
        for(Long ePortfolioPageId : ePortfolioPageIds){
            this.deleteByEPortfolioPageId(ePortfolioPageId);
        }
    }

    /**
     * 删除电子学当中不再引用的文件
     * 由于可以复制电子学档，所以文件可能被多个电子学档引用，首先需排除还在引用的文件后，后方可删除
     * @param userFiles 文件集合
     */
    private void deleteUserFileByEPortfolioPageFileNotIn(List<UserFile> userFiles){
        Map<Long, UserFile> map = userFiles.stream().collect(Collectors.toMap(UserFile::getId, a -> a));
        List<Long> userFileIds = userFiles.stream().map(UserFile::getId).collect(Collectors.toList());
        Set<Long> remainUserFileIds = userEPortfolioPageFileDao.getByUserFileIdIn(userFileIds);

        if(Objects.nonNull(remainUserFileIds)){
            userFileIds.removeAll(remainUserFileIds);
        }

        if(ListUtils.isNotEmpty(userFileIds)){
            for(Long id : userFileIds){
                ossClient.delete(map.get(id).getFileUrl());
            }
            userFileDao.batchDelete(userFileIds);
        }
    }
}
