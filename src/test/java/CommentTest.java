import com.jit.service.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

    @Test
    public void studentTest() {
        System.out.println(commentService.listComment(2));
    }

}
