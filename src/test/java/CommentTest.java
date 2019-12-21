import com.jit.mapper.UserMapper;
import com.jit.service.CommentService;
import com.jit.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.invoke.CallSite;
import java.util.ArrayList;

/**
 * @program: forum
 * @description:
 * @author: XZQ
 * @create: 2019-11-29 18:32
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CommentTest {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @Test
    public void studentTest() {
        System.out.println(commentService.listComment(2));
    }

    @Test
    public void relatedTest() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(3);
        list.add(4);
        list.add(2);
        System.out.println(userMapper.findRelatedUsers(list));
    }

    @Test
    public void findFansTest() {
        System.out.println(userService.findFansList(1));
        System.out.println("111111111111111111111111111");
        System.out.println(userService.findFollowsList(1));
    }

}
