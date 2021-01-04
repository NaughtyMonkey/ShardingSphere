package com.feng.shardingjdbc;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.feng.shardingjdbc.entity.Course;
import com.feng.shardingjdbc.entity.Udict;
import com.feng.shardingjdbc.entity.User;
import com.feng.shardingjdbc.mapper.CourseMapper;
import com.feng.shardingjdbc.mapper.UdictMapper;
import com.feng.shardingjdbc.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShardingjdbcApplicationTests
{
    //注入mapper
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UdictMapper udictMapper;

    //======================测试公共表===================
    //添加操作
    @Test
    public void addDict()
    {
        Udict udict = new Udict();
        udict.setUstatus("a");
        udict.setUvalue("已启用");
        udictMapper.insert(udict);
    }

    //删除操作
    @Test
    public void deleteDict()
    {
        QueryWrapper<Udict> wrapper = new QueryWrapper<>();
        //设置userid值
        wrapper.eq("dictid", 552144666074546177L);
        udictMapper.delete(wrapper);
    }

    //======================测试垂直分库==================
    //添加操作
    @Test
    public void addUserDb()
    {
        User user = new User();
        user.setUsername("Jack");
        user.setUstatus("b");
        userMapper.insert(user);
    }

    //查询操作
    @Test
    public void findUserDb()
    {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //设置userid值
        wrapper.eq("user_id", 552283596010815489L);
        User user = userMapper.selectOne(wrapper);
        System.out.println(user);
    }


    //=======================测试水平分库===================
    //添加课程的方法
    @Test
    public void addCourseDb()
    {
        for (int i = 1; i <= 10; i++)
        {
            Course course = new Course();
            course.setCname("java" + i);
            course.setUserId(100L);
            course.setCstatus("Normal" + i);
            courseMapper.insert(course);
        }
    }

    //查询操作
    @Test
    public void findCourseDb()
    {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        //设置userid值
        wrapper.eq("user_id", 100L);
        //设置cid值L
        wrapper.eq("cid", 552108566027173889L);
        Course course = courseMapper.selectOne(wrapper);
        System.out.println(course);
    }

    //=======================测试水平分表===================
    //添加课程的方法
    @Test
    public void addCourse()
    {
        for (int i = 1; i <= 10; i++)
        {
            Course course = new Course();
            course.setCname("java" + i);
            course.setUserId(101L);
            course.setCstatus("Normal" + i);
            courseMapper.insert(course);
        }
    }

    //查询课程的方法
    @Test
    public void findCourse()
    {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("cid", 550826267901952001L);
        Course course = courseMapper.selectOne(wrapper);
        System.out.println(course);
    }
}
