public class TaxCalculator {

	public static void main(String[] args) {
		new TaxCalculator().calculateTax(<Your Gross Pay (CTC)>);
	}

	private void printLine() {
		for (int i = 0; i < 30; i++) {
			System.out.print("===");
		}
		System.out.println();
	}

	private void calculateTax(long ctc) {

		double monthlypay = ctc / 12;
		double pf = ((monthlypay * 0.40) * 0.12);
		double takeHomeTaxOut = monthlypay - pf;

		System.out.println(ctc + " \t = Your CTC");
		System.out.println(monthlypay + " \t = Monthly pay");
		System.out.println(pf + "\t = PF");
		printLine();
		System.out.println(takeHomeTaxOut + "\t = Take Home (without tax)");
		printLine();

		long tax80c = 150000;
		long tax80ccd = 50000;
		long taxHomeLoan = 200000;
		long taxHRA = 300000;
		long totalTax = tax80c + tax80ccd + taxHomeLoan + taxHRA;
		double taxableIncome = (ctc - totalTax);

		double taxOfTheYear = getTotalTax(taxableIncome);
		double taxPerMonth = taxOfTheYear / 12;

		System.out.println("\n\nTax Deductions \n");
		System.out.println(tax80c + "\t = 80 C");
		System.out.println(tax80ccd + "\t = 80ccd");
		System.out.println(taxHomeLoan + "\t = Home Loan");
		System.out.println(taxHRA + "\t = HRA");
		printLine();
		System.out.println(totalTax + "\t = total Tax deductions");
		printLine();
		System.out.println(taxPerMonth + " \t Per month Tax and total tax per Year is " + taxOfTheYear
				+ " for taxable income of " + taxableIncome + getTaxSlab(taxableIncome));
		printLine();
		System.out.println("\n\n");
		System.out.println(((long)(takeHomeTaxOut-taxPerMonth)) +"\t = Your exact Tax Home after Tax Deduction");

	}

	private double getTotalTax(double taxableIncome) {

		if (taxableIncome >= 250001 && taxableIncome <= 500000) {
			return taxableIncome * 0.05;
		} else if (taxableIncome >= 500001 && taxableIncome <= 1000000) {
			return 12500 + ((taxableIncome - 500000) * 0.2);
		} else if (taxableIncome >= 1000001) {
			return 112500 + ((taxableIncome - 1000000) * 0.3);
		} else {
			return 0;
		}
	}
	
	private String getTaxSlab(double taxableIncome) {

		if (taxableIncome >= 250001 && taxableIncome <= 500000) {
			return "(5% slab)";
		} else if (taxableIncome >= 500001 && taxableIncome <= 1000000) {
			return "(20% slab)";
		} else if (taxableIncome >= 1000001) {
			return "(30% slab)";
		} else {
			return "No Tax";
		}
	}

}
