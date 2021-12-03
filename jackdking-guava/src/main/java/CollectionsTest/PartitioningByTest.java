package CollectionsTest;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;



/**
 * Implementations of {@link Object} that implement various useful test
 * operations, such as testPartitioningBy ,etc.
 *
 * <p>The following are examples of using the predefined collectors to perform
 * common mutable reduction tasks:
 *
 * <pre>{@code
 *     // Accumulate names into a List
 *     List<String> list = people.stream().map(Person::getName).collect(Collectors.toList());
 *
 *     // Accumulate names into a TreeSet
 *     Set<String> set = people.stream().map(Person::getName).collect(Collectors.toCollection(TreeSet::new));
 *
 *     // Convert elements to strings and concatenate them, separated by commas
 *     String joined = things.stream()
 *                           .map(Object::toString)
 *                           .collect(Collectors.joining(", "));
 *
 *     // Compute sum of salaries of employee
 *     int total = employees.stream()
 *                          .collect(Collectors.summingInt(Employee::getSalary)));
 *
 *     // Group employees by department
 *     Map<Department, List<Employee>> byDept
 *         = employees.stream()
 *                    .collect(Collectors.groupingBy(Employee::getDepartment));
 *
 *     // Compute sum of salaries by department
 *     Map<Department, Integer> totalByDept
 *         = employees.stream()
 *                    .collect(Collectors.groupingBy(Employee::getDepartment,
 *                                                   Collectors.summingInt(Employee::getSalary)));
 *
 *     // Partition students into passing and failing
 *     Map<Boolean, List<Student>> passingFailing =
 *         students.stream()
 *                 .collect(Collectors.partitioningBy(s -> s.getGrade() >= PASS_THRESHOLD));
 *
 * }</pre>
 *
 * @since 1.8
 */
public class PartitioningByTest {




    //分区函数
    @Test
    public void testPartitioningBy() {

        ImmutableList<User> userList = ImmutableList.<User>builder()
                .add(User.builder().username("jack").age(12).gender("man").build())
                .add(User.builder().username("john1").age(24).gender("man").build())
                .add(User.builder().username("john2").age(34).gender("man").build())
                .add(User.builder().username("john3").age(46).gender("man").build())
                .add(User.builder().username("john4").age(12).gender("woman").build())
                .add(User.builder().username("john5").age(23).gender("man").build())
                .add(User.builder().username("john6").age(45).gender("man").build())
                .add(User.builder().username("john7").age(67).gender("woman").build())
                .add(User.builder().username("john8").age(56).gender("man").build())
                .build();


        System.out.println("分区函数：按照某一个字段属性的值范围进行分组，分两组，分组后的value为list结构");
        userList.stream().collect(Collectors.partitioningBy(user -> user.getAge()>=18, Collectors.toList()))
        .forEach((k, v) -> System.out.println("key: "+k+"value: "+v));

        System.out.println("分区函数：按照某一个字段属性的值范围进行分组，分两组，分组后的value为set结构");
        userList.stream().collect(Collectors.partitioningBy(user -> user.getAge()>=18, Collectors.toSet()))
                .forEach((k, v) -> System.out.println("key: "+k+" value: "+v));

        System.out.println("分区函数：按照某一个字段属性的值范围进行分组，分两组，分组后的value为计数数据");
        userList.stream().collect(Collectors.partitioningBy(user -> user.getAge()>=18, Collectors.counting()))
                .forEach((k, v) -> System.out.println("key: "+k+" value: "+v));

        System.out.println("reducing,找到年龄最大的人");
        userList.stream().collect(Collectors.reducing(BinaryOperator.maxBy(Comparator.comparingInt(User::getAge))))
                .ifPresent(System.out::println);

        System.out.println("reducing,分组后找到年龄最小的人");
        userList.stream().collect(Collectors.partitioningBy(user -> user.getGender().equals("man"), Collectors.reducing(BinaryOperator.minBy(Comparator.comparingInt(User::getAge)))))
                .forEach((k, v) -> System.out.println("key: "+k+" value: "+v));

        System.out.println("reducing,partitioningBy,按年龄分区后找到年龄最小的人");
        userList.stream().collect(Collectors.partitioningBy(user -> user.getGender().equals("man"), Collectors.counting()))
                .forEach((k, v) -> System.out.println("key: "+k+" value: "+v));


        userList.stream().collect(Collectors.partitioningBy(user -> user.getGender().equals("man"), Collectors.reducing(BinaryOperator.minBy(Comparator.comparingInt(User::getAge)))))
                .forEach((k, v) -> System.out.println("key: "+k+" value: "+v));


        userList.stream().collect(Collectors.partitioningBy(user -> user.getGender().equals("man"), Collectors.minBy(Comparator.comparingInt(User::getAge))))
                .forEach((k, v) -> System.out.println("key: "+k+" value: "+v));




    }
}
