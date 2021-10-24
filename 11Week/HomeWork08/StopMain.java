
public class StopMain {

    public static void main(String[] args) {
        Stock stock = new Stock("stock-key");
        if (stock.decrease(1)) {
            System.out.println("减库存成功");
        } else {
            System.out.println("减库存失败");
        }

        stock.shutdown();
    }
}
