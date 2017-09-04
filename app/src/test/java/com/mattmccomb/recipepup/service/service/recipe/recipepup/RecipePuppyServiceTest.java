package com.mattmccomb.recipepup.service.service.recipe.recipepup;

import com.mattmccomb.recipepup.models.RecipePreview;
import com.mattmccomb.recipepup.service.recipe.RecipeService;
import com.mattmccomb.recipepup.service.recipe.recipepuppy.GetRecipeResponse;
import com.mattmccomb.recipepup.service.recipe.recipepuppy.RecipePuppyApiClient;
import com.mattmccomb.recipepup.service.recipe.recipepuppy.RecipePuppyService;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for the {@link RecipePuppyService} class.
 */
public class RecipePuppyServiceTest {

    private RecipePuppyApiClient mockApiClient;
    private RecipeService service;

    @Before
    public void setUp() {
        mockApiClient = mock(RecipePuppyApiClient.class);
        service = new RecipePuppyService(mockApiClient);
    }

    @Test
    public void testThatSearchReturnsAMaximumNumberOfItems() {
        given(mockApiClient.searchRecipes("muffin", 1)).willReturn(stubGetRecipesResponse(createRecipes(100)));
        List<RecipePreview> results = service.searchRecipes("muffin", 10).blockingFirst();
        assertThat(results.size(), is(equalTo(10)));
    }

    @Test
    public void testThatSearchDoesNotIssueExtraneousNetworkRequests() {
        given(mockApiClient.searchRecipes("muffin", 1)).willReturn(stubGetRecipesResponse(createRecipes(10)));
        given(mockApiClient.searchRecipes("muffin", 2)).willReturn(stubGetRecipesResponse(createRecipes(5)));
        service.searchRecipes("muffin", 30).blockingFirst();
        // Ensure that a unnecessary third API request wasn't issued
        verify(mockApiClient, times(2)).searchRecipes(eq("muffin"), anyInt());
    }

    private Observable<GetRecipeResponse> stubGetRecipesResponse(List<RecipePreview> recipes) {
        return Observable.just(new GetRecipeResponse(null, null, recipes));
    }

    private List<RecipePreview> createRecipes(int count) {
        List<RecipePreview> recipes = new ArrayList<RecipePreview>();
        for (int i = 0; i < count; i++) {
            recipes.add(new RecipePreview(String.format("RecipePreview %d", count), null, null, null));
        }
        return recipes;
    }

}
