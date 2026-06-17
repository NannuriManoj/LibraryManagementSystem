public class Member {
    private String name;
    private final String memberId;
    private MemberType memberType;
    private BorrowHistory borrowHistory;

    public Member(String name, String memberId, MemberType memberType) {
        this.name = name;
        this.memberId = memberId;
        this.memberType = memberType;
        this.borrowHistory = new BorrowHistory();
    }

    public String getName() {
        return name;
    }

    public String getMemberId() {
        return memberId;
    }

    public MemberType getMemberType() {
        return memberType;
    }

    public BorrowHistory getBorrowHistory() {
        return borrowHistory;
    }

    public void borrowBook(Book book){
        if (borrowHistory.contains(book)){
            System.out.println("You have already borrowed the book...");
        } else {
            borrowHistory.addBook(book);
            System.out.println("Book borrowed successfully...");
        }
    }

    public void setMemberType(MemberType memberType) {
        this.memberType = memberType;
    }

    public void returnBook(Book book){
        if (borrowHistory.contains(book)){
            borrowHistory.removeBook(book);
            System.out.println("Book submitted successfully...");
        } else {
            System.out.println("This book is not in your borrow history.");
        }
    }

    public double calculateFine(int daysOverdue){
        return memberType.calculateFine(daysOverdue);
    }
}
