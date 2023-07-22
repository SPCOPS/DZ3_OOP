import java.util.HashMap;
import java.util.LinkeвList;
import java.util.Map;
import java.util.Queue;

@FunctionalInterface
interface StringProcessor {
    String process(String input);
}

interface QueueBehaviour {
    void enqueue(String person);
    String dequeue();
}

interface MarketBehaviour {
    void addPersonToQueue(String person);
    String servePerson();
    void update();
}

class Market implements QueueBehaviour, MarketBehaviour, StringProcessor, Comparable<Market> {
    private Queue<String> queue;
    private Map<String, Integer> products;

    public Market() {
        queue = new LinkedList<>();
        products = new HashMap<>();
        products.put("Молоко", 10);
        products.put("Хлеб", 20);
        products.put("Яблоки", 30);
    }

    @Override
    public void enqueue(String person) {
        queue.add(person);
    }

    @Override
    public String dequeue() {
        return queue.poll();
    }

    @Override
    public void addPersonToQueue(String person) {
        enqueue(person);
    }

    @Override
    public String servePerson() {
        return dequeue();
    }

    @Override
    public void update() {
        System.out.println("Обновление состояния магазина:");
        System.out.println("Очередь клиентов: " + queue);

        int totalOrders = queue.size();
        for (int i = 0; i < totalOrders; i++) {
            String person = servePerson();
            String order = generateOrder();
            processOrder(person, order);
        }

        System.out.println("Товары после обработки заказов:");
        for (Map.Entry<String, Integer> entry : products.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private String generateOrder() {
        String[] productsList = {"Молоко", "Хлеб", "Яблоки"};
        return productsList[(int) (Math.random() * productsList.length)];
    }

    private void processOrder(String person, String order) {
        if (products.containsKey(order) && products.get(order) > 0) {
            System.out.println(person + " заказал " + order + ". Заказ выполнен.");
            products.put(order, products.get(order) - 1);
        } else {
            System.out.println(person + " заказал " + order + ". Товар отсутствует.");
        }
    }

    @Override
    public String process(String input) {
        return input.toUpperCase();
    }

    @Override
    public int compareTo(Market otherMarket) {
        return Integer.compare(this.queue.size(), otherMarket.queue.size());
    }
}

public class DZ3 {
    public static void main(String[] args) {
        Market market = new Market();

        market.addPersonToQueue("Клиент 1");
        market.addPersonToQueue("Клиент 2");
        market.addPersonToQueue("Клиент 3");

        System.out.println("Обслуживание клиентов:");
        System.out.println(market.servePerson());
        System.out.println(market.servePerson());
        System.out.println(market.servePerson());

        market.update();
    }
}
