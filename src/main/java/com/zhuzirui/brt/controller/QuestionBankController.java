package com.zhuzirui.brt.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuzirui.brt.common.Result;
import com.zhuzirui.brt.dao.QuestionMapper;
import com.zhuzirui.brt.mapper.QuestionBankStructMapper;
import com.zhuzirui.brt.mapper.QuestionStructMapper;
import com.zhuzirui.brt.model.dto.QuestionBankDTO;
import com.zhuzirui.brt.model.dto.QuestionDTO;
import com.zhuzirui.brt.model.entity.Question;
import com.zhuzirui.brt.model.entity.QuestionBank;
import com.zhuzirui.brt.model.entity.User;
import com.zhuzirui.brt.service.QuestionBankService;
import com.zhuzirui.brt.service.QuestionExtractorService;
import com.zhuzirui.brt.service.QuestionService;
import com.zhuzirui.brt.service.UserService;
import com.zhuzirui.brt.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import kotlin.Pair;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.zhuzirui.brt.utils.JwtUtil.getJwtTokenFromCookie;

/**
 * <p>
 * 题库信息表，存储题库信息 前端控制器
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */

@Data
@Getter
@Setter
class CreateBodyParser{
    List<Integer> fileIdList;
    private String title;
    private String description;
    private String keywords;

    private String text;

    QuestionBankDTO getQuestionBankDTO(){
        QuestionBankDTO questionBankDTO = new QuestionBankDTO();
        questionBankDTO.setTitle(title);
        questionBankDTO.setDescription(description);
        questionBankDTO.setKeywords(keywords);

        return questionBankDTO;
    }
}


@RestController
@RequestMapping("/brt/questionBank")
public class QuestionBankController {

    @Autowired
    private QuestionBankService questionBankService;

    @Autowired
    private QuestionBankStructMapper questionBankStructMapper;

    @Autowired
    private QuestionExtractorService questionExtractorService;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    JwtUtil jwtUtil;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    UserService userService;

    private static final Logger logger = Logger.getLogger(QuestionBankController.class.getName());
    @Autowired
    private QuestionStructMapper questionStructMapper;



    //根据指定文件id列表生成题库
    @PostMapping("/create")
    public Result<Pair<QuestionBank,List<Question>>> create(@RequestBody CreateBodyParser body,
                                                            HttpServletRequest request) {
        logger.info("Body: " + body.toString());

        List<Integer> fileIdList = body.getFileIdList();
        QuestionBankDTO questionBankDTO = body.getQuestionBankDTO();

        //鉴权
        String jwtToken = getJwtTokenFromCookie(request);// 调用方法从 Cookie 中获取 JWT Token
        Integer userId;
        // 检查 JWT Token 是否存在
        if (jwtToken != null && !jwtToken.isEmpty()) {
            // 使用 JwtUtil 提取用户 ID
            userId = jwtUtil.extractUserId(jwtToken);
            User user = userService.getUserByUserId(userId);
            if(user == null) return Result.error(404, "User not found");
        } else {
            // 如果没有 JWT Token，返回错误信息
            return Result.error(401, "Invalid JWT token");
        }

        //补丁：
        //先不根据文件内容生成题库
        //需要先处理文件，清洗成纯文本
        //....

        //检查所需信息
        if(fileIdList == null || fileIdList.isEmpty()) return Result.error(500, "need at least one file");
        if(questionBankDTO == null) return Result.error(500, "need more information");
        if(questionBankDTO.getTitle() == null || questionBankDTO.getTitle().isEmpty()) return Result.error(500, "need title");
        if(questionBankDTO.getDescription() == null || questionBankDTO.getDescription().isEmpty()) return Result.error(500, "need description");
        if(questionBankDTO.getKeywords() == null || questionBankDTO.getKeywords().equals("[]")) return Result.error(500, "need keywords");

        questionBankDTO.setIsDeleted(false);
        questionBankDTO.setIsPublic(false);
        questionBankDTO.setUserId(userId);
        QuestionBank questionBank = questionBankStructMapper.dtoToEntity(questionBankDTO);
        Integer questionBankId;
        try{
            questionBankId = questionBankService.saveQuestionBank(questionBank);
        }catch (Exception e){
            logger.info("Save questionBank failed.\n"+e.getMessage());
            return Result.error(500, e.getMessage());
        }

        //模拟从文件中获取题库
        logger.info("Generating question bank: " + questionBankId+" ...");
        List<QuestionDTO> questionDTOList = null;
        try{
            String text = body.getText();
            if (text == null || text.isEmpty()) text ="作品鉴赏\n" +
                    "播报\n" +
                    "编辑\n" +
                    "思想内容\n" +
                    "《红楼梦》全面而深刻地反映了封建社会盛极而衰的时代特征。它所描写的不是“洞房花烛、金榜题名”的爱情故事；而是写封建贵族青年贾宝玉、林黛玉、薛宝钗之间的恋爱和婚姻悲剧。小说的巨大社会意义在于它不是孤立地去描写这个爱情悲剧，而是以这个恋爱、婚姻悲剧为中心，写出了当时具有代表性的贾、王、史、薛四大家族的兴衰，其中又以贾府为中心，揭露了封建社会后期的种种黑暗和罪恶，及其不可克服的内在矛盾，对腐朽的封建统治阶级和行将崩溃的封建制度作了有力的批判，使读者预感到它必然要走向覆灭的命运。同时小说还通过对贵族叛逆者的歌颂，表达了新的朦胧的理想。\n" +
                    "《红楼梦》是一部内涵丰厚的作品，《好了歌》和十二支《红楼梦曲》提示着贾宝玉所经历的三重悲剧。作者将贾宝玉和一群身份、地位不同的少女放在大观园这个既是诗化的、又是真实的小说世界里，来展示她们的青春生命和美的被毁灭的悲剧。作品极为深刻之处在于，并没有把这个悲剧完全归于恶人的残暴，其中一部分悲剧是封建势力的直接摧残，如鸳鸯、晴雯、司棋这些人物的悲惨下场，但是更多的悲剧是封建伦理关系中的“通常之道德、通常之人情、通常之境遇”所造成的，是几千年积淀而凝固下来的正统文化的深层结构造成的人生悲剧。\n" +
                    "《红楼梦》描绘了上至皇宫、下及乡村的广阔历史画面，广泛而深刻地反映了封建社会末世复杂深刻的矛盾冲突，显示了封建贵族的本质特征和必然衰败的历史命运。尤其深刻的是，在小说展示的贾府的生活图画里，显示出维持着这个贵族之家的等级、名分、长幼、男女等关系的礼法习俗的荒谬，揭开了封建家族“温情脉脉面纱”内里的种种激烈的矛盾和斗争。\n" +
                    "在中国文学史上，还没有一部作品能把爱情的悲剧写得像《红楼梦》那样富有激动人心的力量；也没有一部作品能像它那样把爱情悲剧的社会根源揭示得如此全面、深刻，从而对封建社会作出了最深刻有力的批判。 [3] [6]\n" +
                    "艺术成就\n" +
                    "《红楼梦》突出的艺术成就，就是“它像生活和自然本身那样丰富、复杂，而且天然浑成”，作者把生活写得逼真而有味道。《红楼梦》里面大事件和大波澜都描写得非常出色，故事在进行，人物性格在显现，洋溢着生活的兴味，揭露了生活的秘密。它的细节描写、语言描写继承发展了前代优秀小说的传统。\n" +
                    "《红楼梦》塑造了众多活生生的人物形象，他们有正面的，有反面的，有主要的，也有次要的，各自具有自己独特的个性特征，而且其中不少形象已流行于生活之中，成为不朽的艺术典型，在中国乃至世界文学史上永远放射着奇光异彩。\n" +
                    "《红楼梦》的情节结构，在以往传统小说的基础上，也有了新的重大的突破。它改变了以往如《水浒传》《西游记》等一类长篇小说情节和人物单线发展的特点，人物事件交错发展，彼此制约，创造了一个宏大完整而又自然的艺术结构，使众多的人物活动于同一空间和时间，并且使情节的推移也具有整体性，表现出作者的艺术才思。\n" +
                    "《红楼梦》的环境描写充满了诗情画意，笔法可谓多样、特色尤为鲜明。在中国古典小说中，融合精彩的环境描写，以刻画人物丰满的形象和鲜明的个性，是作者的独创。作者对人物活动的社会环境没有像一般小说一样进行详细的描写，而是采用似乎不经意实则是别出心裁的粗笔点染，将大范围的社会典型环境的描写、特征融化到小说的细节描写中，让读者感受到大厦将倾的时代变迁和社会生活的特别氛围。\n" +
                    "《红楼梦》语言成熟优美。其特点是简洁而纯净，准确而传神，朴素而多采，达到了炉火纯青的境界。小说中那些写景状物的语言，绘色绘声，使读者仿佛身临其境。\n" +
                    "《红楼梦》无论是在思想内容上或是艺术技巧上都具有自己崭新的面貌，具有永久的艺术魅力，使它足以卓立于世界文学之林而毫不逊色。";

            questionDTOList =  questionExtractorService.extractQuestions(text);

            if(questionDTOList == null || questionDTOList.isEmpty()) return Result.error(500, "Generate questions failed, please try again");
        }catch (Exception e){
            logger.info("Failed to generate question bank: \n" + e.getMessage());
            return Result.error(500, e.getMessage());
        }

        //保存问题
        logger.info("Saving questions...");
        List<Question> questionSavedList = new ArrayList<>();
        for(QuestionDTO questionDTO : questionDTOList){
            questionDTO.setBankId(questionBankId);
            questionBankDTO.setIsPublic(false);
            questionBankDTO.setIsDeleted(false);

            Question question = questionStructMapper.dtoToEntity(questionDTO);
            try{
                questionService.save(question);
                questionSavedList.add(question);
            }catch (Exception e){
                continue;
            }
        }

        //检查是否生成问题
        logger.info("Saved: " + questionSavedList.size());
        if(questionSavedList.isEmpty()) {
            //回滚
            logger.info("No questions were saved. removing questionBank: " + questionBankId);
            questionBankService.removeById(questionBank);
            logger.info("Removed questionBank: " + questionBankId);
            return Result.error(500, "No questions saved");
        }

        return Result.success(new Pair<QuestionBank,List<Question>>(questionBank,questionSavedList));
    }

    @PostMapping("/delete")
    public Result<Boolean> delete() {
        return null;
    }
    @PostMapping("/update")
    public Result<Boolean> update() {
        return null;
    }
    @PostMapping("/list")
    public Result<Boolean> list() {
        return null;
    }
}
