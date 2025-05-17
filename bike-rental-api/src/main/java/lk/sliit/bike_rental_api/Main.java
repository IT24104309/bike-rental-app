//package lk.sliit.bike_rental_api;
//
//import java.time.LocalDate;
//import java.util.List;
//
//public class Main {
//    public static void main(String[] args) {
//        User testUser = new User(5, "SUBSCRIBED", List.of("DRIVER"), 2);
//
//        BikeRentalApiApplication.DiscountRule rule = new BikeRentalApiApplication.DiscountRule(
//                "loyalty123",
//                "Subscribed Driver Weekend Discount",
//                DiscountType.PERCENTAGE,
//                10,
//                true,
//                LocalDate.of(2025, 5, 1),
//                LocalDate.of(2025, 6, 30),
//                1,
//                3,
//                List.of(
//                        new DiscountCondition("rideCount", ComparatorOp.GREATER_THAN_OR_EQUALS, 5),
//                        new DiscountCondition("userType", ComparatorOp.EQUALS, "SUBSCRIBED"),
//                        new DiscountCondition("profiles", ComparatorOp.IN, List.of("DRIVER"))
//                )
//        );
//
//        if (rule.isApplicable(testUser, LocalDate.of(2025, 5, 15))) {
//            double discounted = rule.applyDiscount(1000);
//            System.out.println("Discounted price: LKR " + discounted);
//        } else {
//            System.out.println("Rule not applicable.");
//        }
//    }
//}
//
