package edu.jared.patterns.decorator;

/**
 * Created by IntelliJ IDEA.
 * User: jared
 * Date: 6/6/11
 * Time: 7:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class Soy extends CondimentDecorator {
    private Beverage beverage;

    public Soy(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public double cost() {
        return this.beverage.cost() + .15;
    }

    @Override
    public String getDescription() {
        return this.beverage.getDescription() + ", Soy";
    }
}
