package Tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.TestListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static utils.Helpers.getRandomValueFromStringList;

@Listeners(TestListener.class)
public class SearchTests extends BaseTest {

    private String SEARCHED_TEXT = "blue";
    private String MIN_PRICE = "10";
    private String MAX_PRICE = "12";

    @Test
    public void checkSearchedItemAndSelectedCategory() {
        String randomCategory = getRandomValueFromStringList(homePage.getCategoriesValues());
        homePage.selectCategoryByText(randomCategory);
        homePage.enterSearchText(SEARCHED_TEXT);

        homePage.submitSearchBtn();
        searchPage.closeSearchCoupon();

        String mainCategoryTitle = searchPage.getMainRelatedCategoryTtile();
        assertTrue(mainCategoryTitle.contains(randomCategory), "Main category: " + mainCategoryTitle + "should contain" + randomCategory);

        assertTrue(searchPage.getSearchedTextFromBreadCrumb().contains(SEARCHED_TEXT));
    }

    @Test
    public void checkPriceIntervalFromFirstPage() {
        String randomCategory = getRandomValueFromStringList(homePage.getCategoriesValues());
        homePage.selectCategoryByText(randomCategory);
        homePage.enterSearchText(SEARCHED_TEXT);

        homePage.submitSearchBtn();
        searchPage.closeSearchCoupon();
        searchPage.setPriceInterval(MIN_PRICE, MAX_PRICE);
        List<Double> productsPrice = getDigitsFromPriceString(searchPage.getProductsPriceOnCurrentPage());

        Collections.sort(productsPrice);

        assertTrue(Double.parseDouble(MIN_PRICE) <= productsPrice.get(0), "Smallest price in the list is = " + productsPrice.get(0));
    }

    @Test
    public void checkSearchResultsNumber() {
        String randomCategory = getRandomValueFromStringList(homePage.getCategoriesValues());
        homePage.selectCategoryByText(randomCategory);
        homePage.enterSearchText(SEARCHED_TEXT);

        homePage.submitSearchBtn();
        searchPage.closeSearchCoupon();

        String randomSubCategory = getRandomValueFromStringList(searchPage.getSubCategoriesList());
        searchPage.selectSubCategory(randomSubCategory);

        String randomSubSubCategory = getRandomValueFromStringList(searchPage.getSubCategoriesList());
        searchPage.selectSubCategory(randomSubSubCategory);

        int searchResultsNumber = searchPage.getSearchResultsNumberFromBreadCrumb();
        searchPage.scrollToPagination();

        int productsNumberCalculatedFromPagesNumber = calculateProductsNumberFromPagesNumber();
        assertEquals(productsNumberCalculatedFromPagesNumber, searchResultsNumber, "Actual number is: " + productsNumberCalculatedFromPagesNumber + "while expected is: " + searchResultsNumber);
    }

    public List<Double> getDigitsFromPriceString(List<String> list) {
        List<Double> price = new ArrayList<>();
        for (String element : list) {
            String stringPrice = element.replaceAll("[^\\d+\\.\\-]", "");
            if (stringPrice.contains("-")) {
                int index = stringPrice.indexOf("-");
                double digitPrice = Double.parseDouble(stringPrice.substring(0, index));
                price.add(digitPrice);
            }
        }
        return price;
    }

    public int calculateProductsNumberFromPagesNumber() {
        int totalPagesNumber = searchPage.getTotalPagesNumber();
        if (totalPagesNumber > 1) {
            searchPage.navigateToPageNumber(String.valueOf(totalPagesNumber));
            int productsNumberOnLastPage = searchPage.getProductsNumberFromCurrentPage();
            return 60 * (totalPagesNumber - 1) + productsNumberOnLastPage;
        }
        return searchPage.getProductsNumberFromCurrentPage();
    }
}
