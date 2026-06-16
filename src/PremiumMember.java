public class PremiumMember implements MemberType{
    @Override
    public double calculateFine(int daysOverdue) {
        return daysOverdue * 2.0;
    }
}
