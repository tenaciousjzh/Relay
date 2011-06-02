package edu.jared.patterns.strategy;

public abstract class Duck {
    private FlyBehavior _flyBehavior;
    private QuackBehavior _quackBehavior;

    public Duck() {

    }

    public abstract void display();

    public void performFly() {
        _flyBehavior.fly();
    }

    public void performQuack() {
        _quackBehavior.quack();
    }

    public void swim() {
        System.out.println("All ducks float, even decoys!");
    }

    public void setFlyBehavior(FlyBehavior fb) {
        _flyBehavior = fb;
    }

    public void setQuackBehavior(QuackBehavior qb) {
        _quackBehavior = qb;
    }
}
