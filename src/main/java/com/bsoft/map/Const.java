package com.bsoft.map;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by sky on 2018/5/17.
 */
public class Const {

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        //获取所有变量的值

        //获取单个变量的值
        /*Class clazz = Class.forName("com.qianmingxs.ScoreTable");
        Field field = clazz.getField("FIVE");
        System.out.println( field.getInt(clazz));*/
    }

    private static Map<String,String> dic = new HashMap<>();

    public static String getRealValue(String key){
     return dic.get(key);
    }

    public static final String TRUE = "true";
    public static final String FALSE = "false";

 // 一级 枚举

   public static String ENTRY_TAG_NORMAL_SIGN = "<NORMAL_SIGN>";
   public static String ENTRY_TAG_ABNORMAL_SIGN = "<ABNORMAL_SIGN>";
   public static String ENTRY_TAG_LIS_ITEM = "<LIS_ITEM>";
   public static String ENTRY_TAG_RIS_RESULT = "<RIS_RESULT>";
   public static String ENTRY_TAG_RIS_ITEM = "<RIS_ITEM>";

    public static String ENTRY_TAG_SYMPTOM_P = "<SYMPTOM(+)>";
    public static String ENTRY_TAG_SYMPTOM_U = "<SYMPTOM(?)>";
    public static String ENTRY_TAG_SYMPTOM_N = "<SYMPTOM(-)>";

    public static String ENTRY_TAG_DISEASE_P = "<DISEASE(+)>";
    public static String ENTRY_TAG_DISEASE_U = "<DISEASE(?)>";
    public static String ENTRY_TAG_DISEASE_N = "<DISEASE(-)>";

    public static String ENTRY_TAG_ALLERGY_HISTORY_P = "<ALLERGY_HISTORY(+)>";
    public static String ENTRY_TAG_ALLERGY_HISTORY_U = "<ALLERGY_HISTORY(?)>";
    public static String ENTRY_TAG_ALLERGY_HISTORY_N = "<ALLERGY_HISTORY(-)>";

    public static String ENTRY_TAG_GENETIC_HISTORY_P = "<GENETIC_HISTORY(+)>";
    public static String ENTRY_TAG_GENETIC_HISTORY_U = "<GENETIC_HISTORY(?)>";
    public static String ENTRY_TAG_GENETIC_HISTORY_N = "<GENETIC_HISTORY(-)>";

    public static String ENTRY_TAG_HABIT_P = "<HABIT(+)>";
    public static String ENTRY_TAG_HABIT_U = "<HABIT(?)>";
    public static String ENTRY_TAG_HABIT_N = "<HABIT(-)>";

    public static String ENTRY_TAG_DRUG_P = "<DRUG(+)>";
    public static String ENTRY_TAG_DRUG_U = "<DRUG(?)>";
    public static String ENTRY_TAG_DRUG_N = "<DRUG(-)>";

    public static String ENTRY_TAG_OPERATION_P = "<OPERATION(+)>";
    public static String ENTRY_TAG_OPERATION_U = "<OPERATION(?)>";
    public static String ENTRY_TAG_OPERATION_N = "<OPERATION(-)>";

    public static String ENTRY_TAG_GENERAL_INDICATOR = "<GENERAL_INDICATOR>";
    public static String ENTRY_TAG_ADDITIONAL_INDICATOR = "<ADDITIONAL_INDICATOR>";

    public static String ENTRY_TAG_PATIENT_INFO = "<PATIENT_INFO>";
    public static String ENTRY_TAG_MR_HISTORY = "<M&R_HISTORY>";
    public static String ENTRY_TAG_MENSTRUAL_HISTORY = "<MENSTRUAL_HISTORY>";
    public static String ENTRY_TAG_EXAMINATION = "<EXAMINATION>";

    public static String ENTRY_TAG_RADIOTHERAPY = "<RADIOTHERAPY>";
    public static String ENTRY_TAG_CHEMOTHERAPY = "<CHEMOTHERAPY>";
    public static String ENTRY_TAG_ANESTHESIA = "<ANESTHESIA>";
    public static String ENTRY_TAG_TARGETED_THERAPY = "<TARGETED_THERAPY>";
    public static String ENTRY_TAG_OTHER_THERAPY = "<OTHER_THERAPY>";

    //二级 分类
    public static String SECTION_TAG_INFO_PATIENT = "[D:INFO_PATIENT]";
    public static String SECTION_TAG_OFFICE = "[D:OFFICE]";
    public static String SECTION_TAG_CHIEF_COMPLAINT = "[D:CHIEF_COMPLAINT]";
    public static String SECTION_TAG_PRESENT_HISTORY = "[D:PRESENT_HISTORY]";
    public static String SECTION_TAG_POMF_HISTORY = "[D:P&O&M&F_HISTORY]";
    public static String SECTION_TAG_PAST_HISTORY = "[D:PAST_HISTORY]";

    public static String SECTION_TAG_EXAM_GENERAL = "[D:EXAM_GENERAL]";
    public static String SECTION_TAG_EXAM_ADDITION = "[D:EXAM_ADDITION]";
    public static String SECTION_TAG_DIAGNOSE_ADMISSION = "[D:DIAGNOSE_ADMISSION]";

    public static String SECTION_TAG_DT_ADVICE = "[D:D&T_ADVICE]";
    public static String SECTION_TAG_CONFIRMED_DIAGNOSE = "[D:CONFIRMED_DIAGNOSE]";
    public static String SECTION_TAG_ONSET_CONDITION = "[D:ONSET_CONDITION]";
    public static String SECTION_TAG_DIAGNOSE_PROOF = "[D:DIAGNOSE_PROOF]";
    public static String SECTION_TAG_DIAGNOSE_DIFF = "[D:DIAGNOSE_DIFF]";
    public static String SECTION_TAG_SUSPECTED_DISCUSSION = "[D:SUSPECTED_DISCUSSION]";
    public static String SECTION_TAG_DT_PLAN = "[D:D&T_PLAN]";
    public static String SECTION_TAG_DIAGNOSE_BF_OP = "[D:DIAGNOSE_BF_OP]";
    public static String SECTION_TAG_DIAGNOSE_IN_OP = "[D:DIAGNOSE_IN_OP]";
    public static String SECTION_TAG_DIAGNOSE_AF_OP = "[D:DIAGNOSE_AF_OP]";
    public static String SECTION_TAG_OPERATION_NM = "[D:OPERATION_N&M]";

    public static String SECTION_TAG_SURGEON = "[D:SURGEON]";
    public static String SECTION_TAG_ASSISTANT = "[D:ASSISTANT]";
    public static String SECTION_TAG_ANESTHESIA_NM = "[D:ANESTHESIA_N&M]";
    public static String SECTION_TAG_ANESTHESIA_DOCTOR = "[D:ANESTHESIA_DOCTOR]";
    public static String SECTION_TAG_OPERATION_STEP = "[D:OPERATION_STEP]";
    public static String SECTION_TAG_OPERATION_AF_MEASURE = "[D:OPERATION_AF_MEASURE]";
    public static String SECTION_TAG_ATTENTION_MATTER = "[D:ATTENTION_MATTER]";
    public static String SECTION_TAG_STATUS_ADMISSION = "[D:STATUS_ADMISSION]";
    public static String SECTION_TAG_DT_STEP = "[D:D&T_STEP]";
    public static String SECTION_TAG_DIAGNOSE_DISCHARGE = "[D:DIAGNOSE_DISCHARGE]";
    public static String SECTION_TAG_DISCHARGE_CONDITION = "[D:DISCHARGE_CONDITION]";
    public static String SECTION_TAG_DISCHARGE_ORDER = "[D:DISCHARGE_ORDER]";
    public static String SECTION_TAG_DISEASE_CHANGE= "[D:DISEASE_CHANGE]";
    public static String SECTION_TAG_POSSIBLE_CAP = "[D:POSSIBLE_C&A&P]";
    public static String SECTION_TAG_INDICATION = "[D:INDICATION]";
    public static String SECTION_TAG_RISK = "[D:RISK]";
    public static String SECTION_TAG_OPERATION_PROGRAM = "[D:OPERATION_PROGRAM]";
    public static String SECTION_TAG_PATIENT_OPINION = "[D:PATIENT_OPINION]";
    public static String SECTION_TAG_BLOOD_REACTION_HISTORY = "[D:BLOOD_REACTION_HISTORY]";
    public static String SECTION_TAG_BLOOD_COMPONENT = "[D:BLOOD_COMPONENT]";
    public static String SECTION_TAG_BLOOD_COUNT = "[D:BLOOD_COUNT]";
    public static String SECTION_TAG_SPECIAL_NAME = "[D:SPECIAL_NAME]";
    public static String SECTION_TAG_INFO_OTHER = "[D:INFO_OTHER]";
    public static String SECTION_TAG_UNSURE = "[D:UNSURE]";


    // 三级 枚举
    public static String PROP_TAG_TIME_HAPPEN = "<TIME_HAPPEN>";
    public static String PROP_TAG_TIME_ENDURANCE = "<TIME_ENDURANCE>";
    public static String PROP_TAG_TIME_CHANGE = "<TIME_CHANGE>";
    public static String PROP_TAG_FREQUENCE = "<FREQUENCE>";
    public static String PROP_TAG_SIZE = "<SIZE>";
    public static String PROP_TAG_SHAPE = "<SHAPE>";
    public static String PROP_TAG_TEXTURE = "<TEXTURE>";
    public static String PROP_TAG_BORDER = "<BORDER>";
    public static String PROP_TAG_NATURE = "<NATURE>";
    public static String PROP_TAG_CAUSE_FACTOR = "<CAUSE_FACTOR>";
    public static String PROP_TAG_WORSENING_FACTOR = "<WORSENING_FACTOR>";
    public static String PROP_TAG_UN_FACTOR = "<UN_FACTOR>";
    public static String PROP_TAG_SMELL = "<SMELL>";
    public static String PROP_TAG_FLEXIBILITY = "<FLEXIBILITY>";
    public static String PROP_TAG_DEGREE = "<DEGREE>";
    public static String PROP_TAG_CHANGE = "<CHANGE>";
    public static String PROP_TAG_POSITION = "<POSITION>";
    public static String PROP_TAG_COLOR = "<COLOR>";

    public static String PROP_TAG_PERIOD = "<PERIOD>";
    public static String PROP_TAG_EFFECTIVE_DRUG = "<EFFECTIVE_DRUG>";
    public static String PROP_TAG_PATHOGEN = "<PATHOGEN>";
    public static String PROP_TAG_PATHOLOGIC_TYPE = "<PATHOLOGIC_TYPE>";
    public static String PROP_TAG_TOLERANT_DRUG = "<TOLERANT_DRUG>";
    public static String PROP_TAG_DISEASE_P = "<DISEASE(+)>";
    public static String PROP_TAG_DISEASE_N = "<DISEASE(-)>";
    public static String PROP_TAG_ALLERGEN = "<ALLERGEN>";
    public static String PROP_TAG_DRUG= "<DRUG>";
    public static String PROP_TAG_HABIT_STATUS = "<HABIT_STATUS>";
    public static String PROP_TAG_CONTACT = "<CONTACT>";
    public static String PROP_TAG_DAILY_Q = "<DAILY_Q>";
    public static String PROP_TAG_DOSE = "<DOSE>";
    public static String PROP_TAG_RESULT = "<RESULT>";
    public static String PROP_TAG_EFFICACY = "<EFFICACY>";
    public static String PROP_TAG_DELIEVER_METHOD = "<DELIEVER_METHOD>";
    public static String PROP_TAG_ANESTHESIA = "<ANESTHESIA>";

    public static String PROP_TAG_INDICATOR_VALUE = "<INDICATOR_VALUE>";

    public static String PROP_TAG_PATIENT_BORN_ADDRESS = "<PATIENT_BORN_ADDRESS>";
    public static String PROP_TAG_PATIENT_NATIVE_ADDRESS = "<PATIENT_NATIVE_ADDRESS>";
    public static String PROP_TAG_PATIENT_HEIGHT = "<PATIENT_HEIGHT>";
    public static String PROP_TAG_BORN_HEIGHT = "<BORN_HEIGHT>";
    public static String PROP_TAG_PATIENT_WEIGHT = "<PATIENT_WEIGHT>";
    public static String PROP_TAG_BORN_WEIGHT = "<BORN_WEIGHT>";
    public static String PROP_TAG_TIME_ADMISSIONE = "<TIME_ADMISSION>";
    public static String PROP_TAG_PATIENT_GENDER = "<PATIENT_GENDER>";
    public static String PROP_TAG_PATIENT_NATIONALITY = "<PATIENT_NATIONALITY>";
    public static String PROP_TAG_PATIENT_NATIVE_PLACE = "<PATIENT_NATIVE_PLACE>";
    public static String PROP_TAG_PATIENT_CAREER = "<PATIENT_CAREER>";
    public static String PROP_TAG_TIME_RECORD = "<TIME_RECORD>";

    public static String PROP_TAG_COUPLE_RELATIONSHIP = "<COUPLE_RELATIONSHIP>";
    public static String PROP_TAG_MARRIAGE = "<MARRIAGE>";
    public static String PROP_TAG_CHILDREN_HEALTH = "<CHILDREN_HEALTH>";
    public static String PROP_TAG_TIME_LAST_DELIVERY = "<TIME_LAST_DELIVERY>";
    public static String PROP_TAG_CHILDBEARING = "<CHILDBEARING>";
    public static String PROP_TAG_SPOUSE_HEALTH = "<SPOUSE_HEALTH>";
    public static String PROP_TAG_TIME_EDC = "<TIME_EDC>";
    public static String PROP_TAG_FIRST_PREGNANT = "<FIRST_PREGNANT>";
    public static String PROP_TAG_NUM_BIRTH = "<NUM_BIRTH>";

    public static String PROP_TAG_MENSTRUAL_CYCLE = "<MENSTRUAL_CYCLE>";
    public static String PROP_TAG_MENSTRUAL_CLOT = "<MENSTRUAL_CLOT>";
    public static String PROP_TAG_MENSTRUAL_AMOUNT = "<MENSTRUAL_AMOUNT>";
    public static String PROP_TAG_MENSTRUAL_COLOR = "<MENSTRUAL_COLOR>";
    public static String PROP_TAG_MENSTRUAL_LMP = "<MENSTRUAL_LMP>";

    public static String PROP_TAG_EXAM_TARGET = "<EXAM_TARGET>";
    public static String PROP_TAG_EXAM_REASON = "<EXAM_REASON>";
    public static String PROP_TAG_EXAM_RESULT = "<EXAM_RESULT>";
    public static String PROP_TAG_SIDE_EFFECT = "<SIDE_EFFECT>";
    public static String PROP_TAG_CURATIVE_EFFECT = "<CURATIVE_EFFECT>";
    public static String PROP_TAG_RADIOTHERAPY_TIME = "<RADIOTHERAPY_TIME>";


   static {
    Class clazz = Const.class;
    Field[] fields = clazz.getFields();

    for( Field field : fields ){
  //      if (field.getName().startsWith("")){
     try {
//      System.out.println( field.getName() + " " +field.get(clazz) );
      dic.put(field.getName(), (String) field.get(clazz));
     } catch (IllegalAccessException e) {
      e.printStackTrace();
     }
  //      }
    }
   }

}
