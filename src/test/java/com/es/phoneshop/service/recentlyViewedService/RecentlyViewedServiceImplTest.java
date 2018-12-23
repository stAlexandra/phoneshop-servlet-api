package com.es.phoneshop.service.recentlyViewedService;

import com.es.phoneshop.model.cart.LimitedSizeList;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RecentlyViewedServiceImplTest {
    private final static String RECENTLY_VIEWED_ATTRIBUTE = "viewedProducts";
    private RecentlyViewedService recentlyViewedService;
    @Mock
    private HttpSession session;
    @Mock
    private LimitedSizeList<Product> list;
    @Mock
    private Product product;

    @Before
    public void setUp() {
        recentlyViewedService = RecentlyViewedServiceImpl.getInstance();
    }

    @Test
    public void testGetNewList() {
        LimitedSizeList<Product> list = recentlyViewedService.getList(session);
        verify(session).setAttribute(eq(RECENTLY_VIEWED_ATTRIBUTE), any(LimitedSizeList.class));
        assertNotNull(list);
    }

    @Test
    public void testGetExistingList() {
        when(session.getAttribute(RECENTLY_VIEWED_ATTRIBUTE)).thenReturn(list);

        LimitedSizeList receivedList = recentlyViewedService.getList(session);

        verify(session, never()).setAttribute(eq(RECENTLY_VIEWED_ATTRIBUTE), any());
        assertEquals(list, receivedList);
    }

    @Test
    public void testAddToList() {
        recentlyViewedService.addToList(product, list);

        verify(list).add(product);
    }

}