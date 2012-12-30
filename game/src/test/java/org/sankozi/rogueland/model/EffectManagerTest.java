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
        Player p = getPlayer();
		EffectManager em = EffectManager.forPlayer(p);
		Effect mockedEffect = mock(Effect.class);
		when(mockedEffect.getFinishTime()).thenReturn(1f);
		em.registerEffect(mockedEffect);
		//it can be 0 
		verify(mockedEffect, atLeast(1)).getFinishTime();
		verify(mockedEffect).start(em);
		
		verify(mockedEffect, never()).end(em);
		em.tick();
		verify(mockedEffect).end(em);
	}

	@Test
	public void registerEvent0TimeTest(){
		Player p = getPlayer();
		EffectManager em = EffectManager.forPlayer(p);
		Effect mockedEffect = mock(Effect.class);
		when(mockedEffect.getFinishTime()).thenReturn(0f);
		em.registerEffect(mockedEffect);
		//it can be 0 
		verify(mockedEffect, atLeast(1)).getFinishTime();
		verify(mockedEffect).start(em);
		verify(mockedEffect).end(em);
	}

    private Player getPlayer() {
        Player p = new Player();
        p.setControls(Controls.ALWAYS_WAIT);
        return p;
    }

	/** Effect increases Destroyable.Param.BLUNT_PROT by 2 */
	private class MockedAccessingEffect extends Effect {
		public MockedAccessingEffect(float finishTime) {super(finishTime);}
		
		@Override
		public void start(ParamAccessManager manager) {
			manager.accessDestroyableParam(Destroyable.Param.BLUNT_PROT).setChange(2f);
		}

		@Override
		public void end(ParamAccessManager manager) {
			manager.accessDestroyableParam(Destroyable.Param.BLUNT_PROT).setChange(0f);
		}

		@Override
		public String getObjectName() {
			return "effect/mocked";
		}
	}

	@Test 
	public void accessDestroyableParamTest(){
		Player p = getPlayer();
		EffectManager em = EffectManager.forPlayer(p);
		float before = p.destroyableParam(Destroyable.Param.BLUNT_PROT);
		em.registerEffect(new MockedAccessingEffect(2f));
		float after = p.destroyableParam(Destroyable.Param.BLUNT_PROT);
		assertEquals(2f, after - before, 0.01f);
	}
}
