package com.example.examplemod;

public class TeleporterBlock {
    private Object BlockEntityType;
    private Object MyTeleporterBlock;
    BlockEntityType<MyTeleporterBlockEntity> myTeleporterEntityType = Registry.register(Registry.BLOCK_ENTITY_TYPE, "my_teleporter_block_entity", BlockEntityType.Builder.create(MyTeleporterBlockEntity::new, MyTeleporterBlock.INSTANCE).build(null));
    private Object EnvType;
    private MyTeleporterBlockEntity linkedTeleporter;
Registry.register(Registry.BLOCK, new void Identifier("my_teleporter"), MyTeleporterBlock.INSTANCE);
Registry.register(Registry.ITEM, new void Identifier("my_teleporter"), new BlockItem(MyTeleporterBlock.INSTANCE, new Item.Settings().

    void group(ItemGroup.REDSTONE)));

    @Environment(EnvType.CLIENT)
    public boolean onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            MyTeleporterBlockEntity blockEntity = (MyTeleporterBlockEntity) world.getBlockEntity(pos);
            if (blockEntity != null) {
                blockEntity.openGui(player);
                return true;
            }
        }
        return false;
    }

    public <MyTeleporterBlockEntity> void createLink(MyTeleporterBlockEntity otherTeleporter) {
        this.linkedTeleporter = otherTeleporter;
        otherTeleporter.linkedTeleporter = this;
    }

}

