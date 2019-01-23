package cn.heckman.backend.storedMap;

public class PersistenceQueueDemo {
    public static void main(String[] args) {
        //String.class:value类型
        BdbPersistentQueue<String> queue = new BdbPersistentQueue<String>("D:\\bdb", "test", String.class);
//        queue.offer("first");
//        queue.offer("double");
//        queue.offer("String");
        //获取移除队列
//        String p1 = queue.poll();
//        String p2 = queue.poll();
//        System.out.println(p1);
//        System.out.println(p2);

//        for (int i = 0; i < 100; i++) {
//            queue.offer("first"+i);
//        }

        System.out.println(queue.size());

        //获取不移除队列--每次取出的都是第一个元素
        for (int i = 0; i < queue.size(); i++) {
            System.out.println(queue.poll());
        }
        //String p1 = queue.peek();
        //String p2 = queue.peek();
    }
}
