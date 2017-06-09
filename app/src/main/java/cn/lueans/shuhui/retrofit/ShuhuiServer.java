package cn.lueans.shuhui.retrofit;

import cn.lueans.shuhui.entity.ChapterListEntity;
import cn.lueans.shuhui.entity.ComicsEntity;
import cn.lueans.shuhui.entity.LoginEntity;
import cn.lueans.shuhui.entity.RegisterEntity;
import cn.lueans.shuhui.entity.SubscribeResultEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;




/**
 * Created by 24277 on 2017/5/22.
 */

public interface ShuhuiServer {
    /**
     * 根据ClassifyId 获取不同类别的漫画列表。默认每页30个数据
     * @param classify
     * @param page
     * @return
     */
    @GET("/ComicBooks/GetAllBook")
    Observable<ComicsEntity> getComics(
            @Query("ClassifyId") String classify,
            @Query("PageIndex") int page);

    /**
     * 根据 id 获取一个漫画的所有章节信息
     * @param id
     * @param page
     * @return
     */
    @GET("/ComicBooks/GetChapterList")
    Observable<ChapterListEntity> getComicChapters(
            @Query("id") String id,
            @Query("PageIndex") int page);


    //不明
    @GET("/ComicBooks/GetLastChapterForBookIds")
    Observable<ChapterListEntity> getLateComicChapters(
            @Query("idJson") String ids,
            @Query("PageIndex") int page);

    /**
     * 订阅
     * @param bookId
     * @param isSubscribe
     * @param fromType
     * @return
     */

    @POST("/Subscribe")
    Observable<SubscribeResultEntity> subscribe(
            @Query("bookid") String bookId,
            @Query("isSubscribe") boolean isSubscribe,
            @Query("fromtype") String fromType);

    /**
     * 注册
     * @param email
     * @param password
     * @param fromType
     * @return
     */
    @POST("/UserCenter/Regedit")
    Observable<RegisterEntity> register(
            @Query("Email") String email,
            @Query("Password") String password,
            @Query("FromType") String fromType);

    /**
     * 登陆
     * @param email
     * @param password
     * @param fromType
     * @return
     */


    @POST("/UserCenter/Login")
    Call<LoginEntity> login(
            @Query("Email") String email,
            @Query("Password") String password,
            @Query("FromType") String fromType);

    /**
     * 获取订阅列表
     * @return
     */
    @GET("/ComicBooks/GetSubscribe")
    Observable<ComicsEntity> getSubscribedComics();
    /**
     * 搜索漫画
     * @param keyword
     * @return
     */
    @POST("/ComicBooks/GetAllBook")
    Observable<ComicsEntity> search(@Query("Title") String keyword);

}
