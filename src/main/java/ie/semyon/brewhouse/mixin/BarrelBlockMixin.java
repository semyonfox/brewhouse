package ie.semyon.brewhouse.mixin;

import ie.semyon.brewhouse.BarrelAging;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

// vanilla barrels don't tick, this gives them a server-side ticker for aging
@Mixin(BarrelBlock.class)
abstract class BarrelBlockMixin extends BaseEntityBlock {
	private BarrelBlockMixin(Properties properties) {
		super(properties);
	}

	@Override
	public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return level.isClientSide() ? null : createTickerHelper(type, BlockEntityTypes.BARREL, BarrelAging::tick);
	}
}
