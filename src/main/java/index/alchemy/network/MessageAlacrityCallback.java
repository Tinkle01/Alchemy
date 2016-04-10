package index.alchemy.network;

import index.alchemy.potion.PotionAlacrity;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageAlacrityCallback  implements IMessage, IMessageHandler<MessageAlacrityCallback, IMessage> {

	@Override
	public IMessage onMessage(MessageAlacrityCallback message, MessageContext ctx) {
		PotionAlacrity.callback(ctx.getServerHandler().playerEntity);
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}

}
