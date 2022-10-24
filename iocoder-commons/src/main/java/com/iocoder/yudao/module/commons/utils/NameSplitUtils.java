package com.iocoder.yudao.module.commons.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户名分割，将用户名分割为姓氏+名
 *
 * @author wu kai
 * @since 2022/10/14
 */
public class NameSplitUtils {
    public static final String[] COMPLEX_NAME = {
            "百里", "北堂", "北野", "北宫", "辟闾", "淳于", "成公", "陈生", "褚师", "端木", "东方", "东郭", "东野", "东门", "第五", "大狐", "段干", "段阳", "第二", "东宫", "公孙", "公冶", "公羊", "公良", "公西", "公孟", "高堂", "高阳",
            "公析", "公肩", "公坚", "郭公", "谷梁", "毌将", "公乘", "毌丘", "公户", "公广", "公仪", "公祖", "皇甫", "黄龙", "胡母", "何阳", "夹谷", "九方", "即墨", "梁丘", "闾丘", "洛阳", "陵尹", "冷富", "龙丘", "令狐", "南宫", "南郭",
            "女娲", "南伯", "南容", "南门 ", "南野", "欧阳", "欧侯", "濮阳", "青阳", "漆雕", "亓官", "渠丘", "壤驷", "上官", "少室", "少叔", "司徒", "司马", "司空", "司寇", "士孙", "申屠", "申徒", "申鲜", "申叔", "夙沙", "叔先", "叔仲",
            "侍其", "叔孙", "澹台", "太史", "太叔", "太公", "屠岸", "唐古", "闻人", "巫马", "微生", "王孙", "无庸", "夏侯", "西门", "信平", "鲜于", "轩辕", "相里", "新垣", "徐离", "羊舌", "羊角", "延陵", " 於陵", "伊祁", "吾丘 ", "乐正",
            "诸葛", "颛孙", "仲孙", "仲长", "钟离", "宗政", "主父", "中叔", "左人", "左丘", "宰父", "长儿", "仉督", "单于", "叱干", "叱利", "车非", "独孤", "大野", "独吉", "达奚", "哥舒", "赫连", "呼延", "贺兰", "黑齿", "斛律", "斛粟",
            "贺若", "夹谷", "吉胡", "可频", "慕容", "万俟", "抹捻", "纳兰", "普周", "仆固", "仆散", "蒲察", "屈突", "屈卢", "钳耳", "是云", "索卢", "厍狄", "拓跋", "同蹄", "秃发", "完颜", "宇文", "尉迟", "耶律", "长孙", "常夏", "陈梁",
            "陈林", "曹岳", "邓李", "范姜", "郭罗", "高陈", "胡杨", "黄方", "贺陈", "蒋申", "刘付", "刘谭", "陆费", "陆叶", "钱王", "钱赖", "巫许", "吴刘", "吴沈", "王曹", "有琴", "张包", "张简", "张廖", "钟任", "章项", "司琴", "司秆",
            "水城", "仲贤", "尚官", "石官", "五兆", "秀山", "公保", "千秋", "金狐", "青柳", "太阳", "明哲", "命提", "丰召 ", "尧乐", "瑛黄", "相令", "相望", "洛松", "南方", "韦开", "相呈", "相礼", "阳迟", "张官", "袁州", "淑鸟", "善续",
            "杨若", "羊若", "秋山", "楠龙", "单徒", "迟辟", "宣谈", "相续", "续相", "宁成", "欧南", "泽久", "阎法", "还闵", "游子", "游走", "孔令", "归海"
    };

    public static List<String> nameSplit(String name) {
        boolean flag = false;
        List<String> names = new ArrayList<>();
        if (name.length() > 2) {
            String newName = name.substring(0, 2);
            for (String s : COMPLEX_NAME) {
                if (newName.equals(s)) {
                    names.add(newName);
                    names.add(name.substring(2));
                    flag = true;
                    break;
                }
            }
        }
        if (!flag) {
            names.add(name.charAt(0) + "");
            names.add(name.substring(1));
            return names;
        }
        return names;
    }

//    public static void main(String[] args) {
//        String str = "张廖未";
//        List<String> list = nameSplit(str);
//        System.out.println(list.get(0));
//        System.out.println(list.get(1));
//    }
}
