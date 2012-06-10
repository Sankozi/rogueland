package org.sankozi.rogueland.model;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author sankozi
 */
public class EffectManagerTest {

	@Test
	public void registerEventTest(){
		Player p = new Player(Controls.ALWAYS_WAIT);
		EffectManager em = EffectManager.forPlayer(p);
		Effect mockedEffect = mock(Effect.class);
		when(mockedEffect.getFinishTime()).thenReturn(1f);
		em.registerEffect(mockedEffect);

		verify(mockedEffect, atLeast(1)).getFinishTime();
		verify(mockedEffect).start(em);
		
		em.tick();
		verify(mockedEffect).end(em);
	}
}
