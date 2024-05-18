package com.example.diary.diary_Sql;

import java.time.LocalDateTime;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.diary.diary_Models.DiaryDataModel;
import com.example.diary.diary_Sql.Repository.DiaryInsUpdRepository;
import com.example.diary.diary_Sql.Repository.DiarySelectRepository;

//  Serviceクラス
@Service
public class DiaryService {

    private final DiaryInsUpdRepository dir;
    private final DiarySelectRepository dsr;

    //  コンストラクタ　ここでjdbcにデータベースと接続する情報を入れる
    public DiaryService(){
        DataSource dataSource = DiaryConfig.dataSource();
        JdbcTemplate jdbcTemplate = DiaryConfig.jdbcTemplate(dataSource);
        dir = new DiaryInsUpdRepository(jdbcTemplate);
        dsr = new DiarySelectRepository(jdbcTemplate);
    }

    //  受け取ったモデルクラスを調整して、
    //  リポジトリクラスのインサートメソッドを起動する
    public DiaryDataModel exeInsert(DiaryDataModel ddm) throws DataAccessException{
        LocalDateTime now = LocalDateTime.now();
        ddm.setDateTime(now);
        System.out.println("Service.exeInsertを通過");
        dir.insert(ddm);
        return ddm;
    }

    //  リポジトリクラスのアップデートメソッドを起動する
    public void exeUpdate(DiaryDataModel ddm) throws DataAccessException{
        dir.update(ddm);
    }

    //  リポジトリクラスの一件セレクト文を起動する
    public DiaryDataModel exeSelectOfOne(Long num) throws DataAccessException{
        return dsr.selectOfOne(num);
    }

    //  リポジトリクラスの全件セレクト文を起動する
    public List<DiaryDataModel> exeSelectAll() throws DataAccessException{
        return dsr.selectAll();
    }
}
