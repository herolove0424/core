package hello.core.singleton;

public class StatefulService {

  private int price; //상태를 유지하는 필드

  public void order(String name, int price){
    System.out.println("name = " + name + ", price = " + price);
    this.price = price; //여기가 문제 !, 공유 필드의 값을 변경할 수 있기 떄문에, 값이 변경되어버리는 문제가 생긴다.
  }

  //문제를 해결하는 방법 ? int로 해서 걍 바로 price를 도출해버리면된다
  public int order2(String name, int price){
    System.out.println("name = " + name + ", price = " + price);
    return price;
  }

  public int getPrice() {
    return price;
  }
}
