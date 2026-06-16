public class RegularMember implements MemberType{
    @Override
    public double calculateFine(int daysOverdue){
        return daysOverdue * 5.0;
    }
}
