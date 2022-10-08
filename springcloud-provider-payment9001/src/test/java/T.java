/**
 * @author: 小e
 * @since: 2022/9/27 20:41
 * @description:
 */
public class T {
    public static void main(String args[]) {
    String a="123";
    String b="123";
    String c=new String("123");
    String d=new String("123");
        System.out.println(a==b);
        System.out.println(a==c);
        System.out.println(a==d);
        System.out.println(a.equals(b));
        System.out.println(a.equals(c));
        System.out.println(c.equals(d));
    }
}
