package com.bsoft.map;

import com.bsoft.accordion.core.proxy.ProxyCenter;
import com.bsoft.accordion.core.proxy.TestFace;
import com.bsoft.accordion.core.proxy.TimerWrapper;
import com.bsoft.accordion.core.proxy.Wrapper;
import com.bsoft.map.model.DocTree;
import com.bsoft.map.model.WordEntry;
import com.bsoft.map.model.WordProcessor;
import com.bsoft.map.xml.XMLParser;
import com.bsoft.util.TextStructApiWraper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static java.lang.System.currentTimeMillis;

/**
 * Created by sky on 2018/5/24.
 */
//class TestCase implements TestFace {
//
//    public static String context = "姓名：***\n" +
//            "\n" +
//            "\n" +
//            "性别：男\n" +
//            "工作单位：#\n" +
//            "\n" +
//            "年龄：43岁\n" +
//            "入院日期：2017.08.30 14:54:\n" +
//            "\n" +
//            "婚姻：已婚\n" +
//            "病史采集日期：2017.08.30 14:54:\n" +
//            "\n" +
//            "民族：汉族\n" +
//            "病史陈述者：患者本人\n" +
//            "\n" +
//            "出生地：***\n" +
//            "病史可靠程度：可靠\n" +
//            "\n" +
//            "职业(工种)：职员\n" +
//            "过敏史：未发现\n" +
//            "    主  诉:右侧腰背部疼痛10天，再发4小时。\n" +
//            "    现病史:缘于入院前10天无明显诱因出现右侧腰背部疼痛，以绞痛为主，表现为辗转反侧、大汉淋漓，程度尚较剧,伴肉眼血尿，无尿频、尿急、尿痛，无畏冷、发热，无恶心、呕吐，无排尿困难，就诊我院急诊科，急查全腹CT示：右输尿管上段小结石（0.4cm）伴轻度肾积水。考虑“肾绞痛”，予以“诺思帕解痉、强痛定止痛、莫西沙星消炎”等处理。上诉症状稍缓解。于我科急会诊，遂在局麻下行“右输尿管DJ管置入术”，术后疼痛症状明显好转。4小时前，右侧腰部再发疼痛，程度尚可，遂就诊我院，门诊拟\"右肾绞痛\"收治入院。发病以来，精神、睡眠、食欲尚可，小便如上述，大便正常，体重无明显增减。\n" +
//            "    既往史:18年前，因“左肾结石伴左肾重度积水、左肾衰竭”，于四川省颖洲医院行“左肾切除术”，术顺，术后顺利出院。否认\"伤寒、肺结核\"等传染病病史，否认\"高血压、冠心病、糖尿病\"病史，否认重大外伤、输血史、手术史，未发现食物、药物过敏史，预防接种史不详。\n" +
//            "    个人史:出生、成长于原籍，无疫水、疫区、放射性物质接触史，否认烟酒史，否认冶游史。\n" +
//            "    婚育史:已婚已育，育有1子3女，爱人及子女均体健。\n" +
//            "    家族史:父母已故，死因不详，否认家族中有其他遗传性、传染性疾病史。\n" +
//            "体 格 检 查\n" +
//            "       T:37.0℃，        P:100次/分，        R：20次/分，         BP：150/94mmHg，\n" +
//            "    一般情况:神志清楚，精神好，发育正常，营养一般，体型正常，外观正常，表情自如，自动体位，查体合作，对答切题，步行入院。\n" +
//            "    皮肤粘膜:皮肤未见黄染，未见皮下出血，未见瘀斑。皮温正常，湿度正常，无水肿，未见肝掌，未见蜘蛛痣。\n" +
//            "    淋巴结:全身浅表淋巴结未触及。\n" +
//            "    头颅:头颅正常，头发分布正常，眼睑无浮肿，眼球无异常，巩膜无黄染，结膜正常，角膜透明，瞳孔等大等圆，直径3mm，对光反射灵敏，外耳道正常，无异常分泌物，听力正常，无乳突压痛，鼻腔通气畅，无副鼻窦区压痛，鼻唇沟对称，口唇无紫绀，伸舌居中，咽无充血，扁桃体无肿大，无脓点。\n" +
//            "    颈部:颈柔软，颈静脉无怒张，肝颈静脉回流征阴性，未见颈动脉异常搏动，气管居中，甲状腺无肿大,无血管杂音。\n" +
//            "    胸部:胸廓无畸形，胸壁静脉未见曲张，无皮下气肿，胸骨无压痛，呼吸运动对称，肋间隙正常。胸廓无畸形，胸廓挤压征阴性。\n" +
//            "    肺部:双侧呼运动匀称，双侧触觉语颤均强,叩诊音清，呼吸音清，未闻及干湿罗音,未闻及胸膜摩擦音。肺下界于锁骨中线、腋中线和肩胛下角线分别为6、8、10肋间,未见异常。\n" +
//            "    心脏:无心前区隆起,心尖搏动正常，位于第v肋间，左锁骨中线内0.5cm，未扪及震颤及抬举样搏动，心界叩诊无扩大，心浊音界正常，心音清晰，心率100次/分，节律整齐。心浊音界如下图所示：\n" +
//            "                                            右(CM)      肋间          左（CM）\n" +
//            "                                            2.0          Ⅱ            2.0\n" +
//            "                                            2.0          Ⅲ            3.0\n" +
//            "                                            3.0          Ⅳ            5.5\n" +
//            "                                                         Ⅴ            8.0\n" +
//            "                                                注：左锁骨中线距前正中线9.5 cm\n" +
//            "    周围血管:脉率100次/分，节律整齐，无脉搏短绌，无奇脉，无交替脉，无毛细血管搏动征，无Duroziez二重杂音，无大血管枪击音，无水冲脉。双侧足背动脉及双侧桡动脉搏动良好、对称。\n" +
//            "    腹部:腹部平坦，呼吸运动自如，腹壁静脉未见怒张，腹壁皮肤无皮疹，无色素沉着，无腹纹，左上腹可见一长约20cm陈旧性疤痕，愈合好。无疝，未见胃肠型及蠕动波，无上腹部搏动。全腹软，无肌紧张，无压痛及反跳痛，未触及包块，肝脏右肋下、剑突下未触及，胆囊未触及，莫菲氏征阴性，腹部叩诊呈鼓音，肝上界位于右锁骨中线上第V肋间，肝区无叩击痛，胃泡鼓音区正常,移动性浊音阴性。肠鸣音4次/分，未闻及振水音及血管杂音。\n" +
//            "    肛门生殖器:肛门直肠及外生殖器，见泌尿专科检查。\n" +
//            "    脊柱四肢:脊柱生理弯曲正常，活动度正常，腰椎无压痛，无叩击痛；各关节四肢形态正常。肢体无浮肿，无静脉曲张，无色素沉着，无溃疡。\n" +
//            "    神经系统:四肢肌张力正常，腹壁反射、肱二头肌、肱三头肌、桡反射、膝反射、跟腱反射均正常，巴彬斯基征未引出，脑膜刺激征阴性。 \n" +
//            "专 科 情 况\n" +
//            "肾脏：双肾区平坦，双肾肋下未触及，左肾区叩击痛,右肾区无叩击痛，未闻及血管杂音。\n" +
//            "输尿管：双侧输尿管径路无压痛。\n" +
//            "膀胱：膀胱区平坦，未触及胀大膀胱，叩诊呈鼓音。\n" +
//            "前列腺：未触及肿大，中间沟存在，质地中等，表面光滑，未触及明显结节，无压痛。\n" +
//            "外生殖器：阴毛男性分布，阴茎发育正常，未见肿物，尿道外口无红肿，未见异常分泌物，双侧阴囊无肿大，睾丸、附睾大小正常，无结节及压痛，双侧精索静脉无曲张。\n" +
//            "辅 助 检 查\n" +
//            "2017-08-20 我院全腹CT示：1.右输尿管上段小结石（0.4cm）伴轻度肾积水，右肾输尿管周围渗出影及少量积液（炎症？）2.左肾 未见3.前列腺稍增大。\n" +
//            "    出院诊断：                               初步诊断:\n" +
//            "                                                 右肾绞痛待查\n" +
//            "                                                 左肾切除术后\n" +
//            "                                                 前列腺增生\n" +
//            "                                                      医生签名：\n" +
//            "第 3 页\n" +
//            "\n" +
//            "\n" +
//            "\n" +
//            "入 院 记 录\n" +
//            "\n" +
//            "姓名：***        科室：泌尿科        病区床号：17j13     病案号：965428\n" +
//            "";
//
//    static DocTree tree;
//
//    static XMLParser paser;
//
//    static ExecutorService threadPool = Executors.newFixedThreadPool(50);
//
//    static {
//        String text = TextStructApiWraper.getResponse(context);
//        tree = new DocTree(text);
//        paser = new XMLParser();
//        paser.parseXml();
//    }
//
//    public static void main(String []  args){
//        TestFace test = (TestFace) ProxyCenter.getProxy(new TestCase(), new TimerWrapper());
//
////        for (int i = 0; i < 10; i++) {
//            test.test2();
////            test.test1();
////        }
//
//        threadPool.shutdown();
//    }
//
//
//    @Override
//    public void test1() {
//        List<Future<Map<String,Object>>> results = new ArrayList<>();
//        Map<String, Object> result = new HashMap<>();
//        for (final WordEntry entry:paser.parseXml()) {//TODO 将集合好的entry 做多线程并发处理 生成一个 result map
//            results.add(threadPool.submit(new Callable<Map<String,Object>>() {
//                @Override
//                public Map<String, Object> call() throws Exception {
//                    Map<String, Object> key_value = new HashMap<String, Object>();
//                    key_value.put(entry.getSection()+"-"+entry.getField(), ""+ WordProcessor.getNLPValue(tree, entry));
//                    return key_value;
//                }
//            }));
//        }
//        for (Future<Map<String,Object>> re:results){
//            try {
//                result.putAll(re.get());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println("multiple thread case: "+result);
//    }
//
//    @Override
//    public void test2() {
//        Map<String, Object> result = new HashMap<>();
//        for (final WordEntry entry:paser.parseXml()) {//经 测试 在循环在 100+ 左右的情况下， 多线程性能不如 单线程性能，原因可能是线程切换、创建的开销大于串行执行开销；
//            result.put(entry.getSection()+"-"+entry.getField(), ""+WordProcessor.getNLPValue(tree, entry));
//        }
//        System.out.println("single thread case: "+result);
//    }
//}
