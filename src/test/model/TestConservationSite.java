package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


public class TestConservationSite {

    private ConservationSite conservationSite1;
    private Wildlife wildlife1;
    private Wildlife wildlife2;

    private Donor donor1;
    private Donor donor2;
    private Donor donor3;
    private Donor donor4;


    @BeforeEach
    public void setup() {
        conservationSite1 = new ConservationSite();
        wildlife1 = new Wildlife("cat", 2000, ConservationStatus.CD, LocalDate.now());
        wildlife2 = new Wildlife("dog", 1000, ConservationStatus.EN, LocalDate.now());
        donor1 = new Donor("jon26", "jona@ubc.ca");
        donor2 = new Donor("234", "great@ubc.ca");
        donor3 = new Donor("12", "12@ubc.ca");
        donor4 = new Donor("43", "iwt@ubc.ca");

    }


    @Test
    public void testConstructor(){
        assertEquals(0, conservationSite1.getTotalFundingRaised());
        assertEquals(0, conservationSite1.getTotalTargetFunding());
        assertEquals(0, conservationSite1.getWildlifeListFullyFunded().size());
        assertEquals(0, conservationSite1.getListOfWildlifeNotFullyFunded().size());
        assertEquals(0, conservationSite1.getListOfDonors().size());
        assertEquals("Wildlife Conservation Facility", conservationSite1.getName());

    }

    @Test
    public void testAddWildlifeTwoDifferentWL() {
        conservationSite1.addWildlife(wildlife1);
        conservationSite1.addWildlife(wildlife2);
        assertEquals(2, conservationSite1.getListOfAllWL().size());
        assertFalse(conservationSite1.getWildlifeListFullyFunded().contains(wildlife1));
        assertFalse(conservationSite1.getWildlifeListFullyFunded().contains(wildlife2));
        assertTrue(conservationSite1.getListOfWildlifeNotFullyFunded().contains(wildlife1));
        assertTrue(conservationSite1.getListOfWildlifeNotFullyFunded().contains(wildlife2));

    }

    @Test
    public void testAddWildlifeDuplicateWL() {
        conservationSite1.addWildlife(wildlife1);
        conservationSite1.addWildlife(wildlife1);
        assertEquals(1, conservationSite1.getListOfWildlifeNotFullyFunded().size());
        assertTrue(conservationSite1.getListOfWildlifeNotFullyFunded().contains(wildlife1));
        assertFalse(conservationSite1.getListOfWildlifeNotFullyFunded().contains(wildlife2));
    }


    @Test
    public void testMoveWLToFullyFundedList() {
        conservationSite1.addWildlife(wildlife1);
        assertNull(wildlife1.getDateFullyFunded());
        assertFalse(conservationSite1.getWildlifeListFullyFunded().contains(wildlife1));

        conservationSite1.moveWildlifeToFullyFundedList(wildlife1);
        assertEquals(LocalDate.now(), wildlife1.getDateFullyFunded());
        assertTrue(conservationSite1.getWildlifeListFullyFunded().contains(wildlife1));
        assertFalse(conservationSite1.getListOfWildlifeNotFullyFunded().contains(wildlife1));
    }

    @Test
    public void testGetListOfAllWLBothListsNonEmpty() {
        conservationSite1.addWildlife(wildlife1);
        conservationSite1.addWildlife(wildlife2);
        conservationSite1.moveWildlifeToFullyFundedList(wildlife2);

        assertTrue(conservationSite1.getListOfAllWL().contains(wildlife1));
        assertTrue(conservationSite1.getListOfAllWL().contains(wildlife2));
        assertEquals(2, conservationSite1.getListOfAllWL().size());

    }

    @Test
    public void testGetListOfAllWLFullyFundedListEmpty() {
        conservationSite1.addWildlife(wildlife1);
        conservationSite1.addWildlife(wildlife2);

        assertTrue(conservationSite1.getListOfAllWL().contains(wildlife1));
        assertTrue(conservationSite1.getListOfAllWL().contains(wildlife2));
        assertEquals(2, conservationSite1.getListOfAllWL().size());

    }

    @Test
    public void testGetListOfAllWLNonFullyFundedListEmpty() {
        conservationSite1.addWildlife(wildlife1);
        conservationSite1.addWildlife(wildlife2);
        conservationSite1.moveWildlifeToFullyFundedList(wildlife2);
        conservationSite1.moveWildlifeToFullyFundedList(wildlife1);

        assertTrue(conservationSite1.getListOfAllWL().contains(wildlife1));
        assertTrue(conservationSite1.getListOfAllWL().contains(wildlife2));
        assertEquals(2, conservationSite1.getListOfAllWL().size());

    }


    @Test
    public void testAddSameDonorTwice() {
        conservationSite1.addDonor(donor1);
        conservationSite1.addDonor(donor2);
        conservationSite1.addDonor(donor2);

        assertEquals(2, conservationSite1.getDonorIDList().size());
        assertTrue(conservationSite1.getListOfDonors().contains(donor1));
        assertTrue(conservationSite1.getListOfDonors().contains(donor2));
    }


    @Test
    public void testUpdateRaisedFunding() {
        conservationSite1.addWildlife(wildlife1);
        conservationSite1.addWildlife(wildlife2);
        wildlife1.setAmountFunded(300);
        wildlife2.setAmountFunded(200);
        conservationSite1.updateRaisedFunding();
        assertEquals(500, conservationSite1.getTotalFundingRaised());
    }



    @Test
    public void testGetDonorIDList() {
        conservationSite1.addDonor(donor2);
        conservationSite1.addDonor(donor1);
        assertEquals(2, conservationSite1.getDonorIDList().size());
        assertTrue(conservationSite1.getDonorIDList().contains("jon26"));
        assertTrue(conservationSite1.getDonorIDList().contains("234"));
    }


    @Test
    public void testFundsRaisedCalculatorZero() {
        conservationSite1.addWildlife(wildlife1);
        conservationSite1.addWildlife(wildlife2);
        assertEquals(0, conservationSite1.fundsRaisedCalculator());
    }


    @Test
    public void testFundsRaisedCalculator() {
        conservationSite1.addWildlife(wildlife1);
        conservationSite1.addWildlife(wildlife2);
        wildlife2.setAmountFunded(200);
        assertEquals(200, conservationSite1.fundsRaisedCalculator());
    }


    @Test
    public void testMostGenerousDonorSingleDonor() {
        donor1.setTotalFundingDonated(98);
        donor2.setTotalFundingDonated(99);
        donor3.setTotalFundingDonated(100);
        conservationSite1.addDonor(donor1);
        conservationSite1.addDonor(donor2);
        conservationSite1.addDonor(donor3);
        assertEquals(1, conservationSite1.mostGenerousDonor().size());
        assertTrue(conservationSite1.mostGenerousDonor().contains(donor3));
    }


    @Test
    public void testMostGenerousMultipleDonors() {
        donor1.setTotalFundingDonated(98);
        donor2.setTotalFundingDonated(100);
        donor3.setTotalFundingDonated(100);
        conservationSite1.addDonor(donor1);
        conservationSite1.addDonor(donor2);
        conservationSite1.addDonor(donor3);
        assertEquals(2, conservationSite1.mostGenerousDonor().size());
        assertTrue(conservationSite1.mostGenerousDonor().contains(donor2));
        assertTrue(conservationSite1.mostGenerousDonor().contains(donor3));
    }

    @Test
    public void testMostGenerousDonorMultipleDonors() {
        donor1.setTotalFundingDonated(98);
        donor2.setTotalFundingDonated(100);
        donor3.setTotalFundingDonated(100);
        donor4.setTotalFundingDonated(1002);
        conservationSite1.addDonor(donor1);
        conservationSite1.addDonor(donor2);
        conservationSite1.addDonor(donor3);
        conservationSite1.addDonor(donor4);

        assertEquals(1, conservationSite1.mostGenerousDonor().size());
        assertTrue(conservationSite1.mostGenerousDonor().contains(donor4));
    }


    @Test
    public void testFundsRaisedCalculatorLargerThanZero() {
        conservationSite1.addWildlife(wildlife1);
        conservationSite1.addWildlife(wildlife2);
        wildlife1.raiseFund(100);
        wildlife2.raiseFund(200);
        assertEquals(wildlife1.getAmountFunded() + wildlife2.getAmountFunded(), conservationSite1.fundsRaisedCalculator());

    }

    @Test
    public void testTargetFundingCalculator() {
        conservationSite1.addWildlife(wildlife1);
        conservationSite1.addWildlife(wildlife2);

        assertEquals(wildlife1.getTargetFunding() + wildlife2.getTargetFunding(), conservationSite1.targetFundingCalculator());

    }


}