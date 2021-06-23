package com.drools.quickstart;

import com.drools.quickstart.entity.ComparisonOperatorEntity;
import com.drools.quickstart.entity.Order;
import com.drools.quickstart.entity.Student;
import com.drools.quickstart.service.UserService;
import org.drools.core.base.RuleNameEqualsAgendaFilter;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ipipman on 2021/6/22.
 *
 * @version V1.0
 * @Package com.drools
 * @Description: (用一句话描述该文件做什么)
 * @date 2021/6/22 1:51 下午
 */
public class DroolsTest {

    @Test
    public void test1() {

        KieServices kieServices = KieServices.Factory.get();
        // 获得Kie容器对象
        KieContainer kieContainer = kieServices.newKieClasspathContainer();
        // 从Kie容器对象中获取会话对象,会话对象，用于和规则引擎交互
        KieSession session = kieContainer.newKieSession();

        // 构造一个对象(Fact对象，事实对象)
        Order order = new Order();
        order.setOriginalPrice(210D);

        // 将数据提供给规则引擎，规则引擎会根据提供的数据进行规则匹配
        session.insert(order);

        //激活规则引擎，如果规则匹配成功则执行规则
        session.fireAllRules();

        //关闭会话
        session.dispose();
    }

    //测试比较操作符
    @Test
    public void test3() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieClasspathContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieClasspathContainer.newKieSession();

        ComparisonOperatorEntity comparisonOperatorEntity = new ComparisonOperatorEntity();
        comparisonOperatorEntity.setNames("张三");
        List<String> list = new ArrayList<String>();
        list.add("张三");
        list.add("李四");
        comparisonOperatorEntity.setList(list);

        //将数据提供给规则引擎，规则引擎会根据提供的数据进行规则匹配，如果规则匹配成功则执行规则
        kieSession.insert(comparisonOperatorEntity);

        kieSession.fireAllRules();
        kieSession.dispose();
    }

    //执行指定规则
    @Test
    public void test4() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieClasspathContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieClasspathContainer.newKieSession();

        ComparisonOperatorEntity comparisonOperatorEntity = new ComparisonOperatorEntity();
        comparisonOperatorEntity.setNames("张三");
        List<String> list = new ArrayList<String>();
        list.add("张三");
        list.add("李四");
        comparisonOperatorEntity.setList(list);
        kieSession.insert(comparisonOperatorEntity);

        //通过规则过滤器实现只执行指定规则
        kieSession.fireAllRules(new RuleNameEqualsAgendaFilter("rule_comparison_memberOf"));

        kieSession.dispose();
    }

    //测试RHS部分的update方法
    @Test
    public void test5() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieClasspathContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieClasspathContainer.newKieSession();

        Student student = new Student();
        student.setAge(5);

        //将数据提供给规则引擎，规则引擎会根据提供的数据进行规则匹配，如果规则匹配成功则执行规则
        kieSession.insert(student);

        kieSession.fireAllRules();
        kieSession.dispose();
    }

    //测试RHS部分的insert方法
    @Test
    public void test6() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieClasspathContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieClasspathContainer.newKieSession();

        Student student = new Student();
        student.setAge(10);

        //将数据提供给规则引擎，规则引擎会根据提供的数据进行规则匹配，如果规则匹配成功则执行规则
        kieSession.insert(student);

        kieSession.fireAllRules();
        kieSession.dispose();
    }

    //测试rule属性no-loop
    @Test
    public void test7() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieClasspathContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieClasspathContainer.newKieSession();

        Student student = new Student();
        student.setAge(25);

        //将数据提供给规则引擎，规则引擎会根据提供的数据进行规则匹配，如果规则匹配成功则执行规则
        kieSession.insert(student);

        kieSession.fireAllRules();
        kieSession.dispose();
    }

    //测试rule属性activation-group，激活分组
    @Test
    public void test8() {
        //activation-group属性是指激活分组，取值为String类型。具有相同分组名称的规则只能有一个规则被触发。
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieClasspathContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieClasspathContainer.newKieSession();
        kieSession.fireAllRules();
        kieSession.dispose();
    }

    //测试rule属性agenda-group，议程分组，需要指定焦点
    @Test
    public void test9() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieClasspathContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieClasspathContainer.newKieSession();

        //设置焦点，对应agenda-group分组中的规则才可能被触发
        kieSession.getAgenda().getAgendaGroup("myagendagroup_1").setFocus();

        kieSession.fireAllRules();
        kieSession.dispose();
    }

    //测试rule属性timer，定时器
    @Test
    public void test10() throws Exception {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieClasspathContainer = kieServices.getKieClasspathContainer();
        final KieSession kieSession = kieClasspathContainer.newKieSession();

        new Thread(new Runnable() {
            public void run() {
                //启动规则引擎进行规则匹配，直到调用halt方法才结束规则引擎
                kieSession.fireUntilHalt();
            }
        }).start();

        Thread.sleep(10000);
        //结束规则引擎
        kieSession.halt();
        kieSession.dispose();
    }

    //测试rule高级语法global
    @Test
    public void test11() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieClasspathContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieClasspathContainer.newKieSession();

        //设置全局变量，名称和类型必须和规则文件中定义的全局变量名称对应
        kieSession.setGlobal("userService", new UserService());
        kieSession.setGlobal("count", 5);
        List list = new ArrayList();    //size为0
        list.add("ipman");
        kieSession.setGlobal("gList", list);

        kieSession.fireAllRules();
        kieSession.dispose();

        //因为在规则中为全局变量添加了两个元素，所以现在的size为2
        System.out.println(list.size());
    }

    //query查询提供了一种查询working memory中符合约束条件的Fact对象的简单方法。
    // 它仅包含规则文件中的LHS部分，不用指定“when”和“then”部分并且以end结束。
    @Test
    public void test12() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieClasspathContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieClasspathContainer.newKieSession();

        Student student1 = new Student();
        student1.setName("张三");
        student1.setAge(12);

        Student student2 = new Student();
        student2.setName("李四");
        student2.setAge(8);

        Student student3 = new Student();
        student3.setName("王五");
        student3.setAge(22);

        //将对象插入Working Memory中
        kieSession.insert(student1);
        kieSession.insert(student2);
        kieSession.insert(student3);

        //调用规则文件中的查询
        QueryResults results1 = kieSession.getQueryResults("query_1");
        int size = results1.size();
        System.out.println("size=" + size);
        for (QueryResultsRow row : results1) {
            Student student = (Student) row.get("$student");
            System.out.println(student);
        }

        //调用规则文件中的查询
        QueryResults results2 = kieSession.getQueryResults("query_2", "王五");
        size = results2.size();
        System.out.println("size=" + size);
        for (QueryResultsRow row : results2) {
            Student student = (Student) row.get("$student");
            System.out.println(student);
        }
        //kieSession.fireAllRules();
        kieSession.dispose();
    }
}
