import './Conversation.sass'

interface ConversationProp {
    index: number
}

export default function Conversation(prop: ConversationProp) {

    return <>
        {/* <div className="load-previous">
            <img src="/assets/up_arrow_icon_2.png" width={'30px'} />
        </div> */}
        <div className="message-group-wrapper right">
            <div className="message-group">
                <div className="single-message single-message-right">
                    <p className="message">Hello World { prop.index }</p>
                    <p className="message-metadata message-metadata-right">23:34</p>
                </div>
                <div className="single-message single-message-right">
                    <p className="message">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries</p>
                    <p className="message-metadata message-metadata-right">23:34</p>
                </div>
                <div className="single-message single-message-right">
                    <p className="message">It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here'.</p>
                    <p className="message-metadata message-metadata-right">23:35</p>
                </div>
            </div>
            <div className="message-avatar-container">
                <img alt="contact's avatar" className="contact-avatar-img" src={'/assets/avatar_1.png'} />
            </div>
        </div>

        <div className="date-separator">
            <p>- Monday 23.09.2022 -</p>
        </div>

        <div className="message-group-wrapper left">
            <div className="message-avatar-container">
                <img alt="contact's avatar" className="contact-avatar-img" src={'/assets/avatar_1.png'} />
            </div>
            <div className="message-group">
                <div className="single-message single-message-left">
                    <p className="message single-message-left">Hello World</p>
                    <p className="message-metadata message-metadata-left">23:34</p>
                </div>
                <div className="single-message single-message-left">
                    <p className="message">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries</p>
                    <p className="message-metadata message-metadata-left">23:34</p>
                </div>
                <div className="single-message single-message-left">
                    <p className="message">It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here'.</p>
                    <p className="message-metadata message-metadata-left">23:35</p>
                </div>
            </div>
        </div>

        <div className="date-separator">
            <p>- Monday 23.09.2022 -</p>
        </div>

        <div className="message-group-wrapper right">
            <div className="message-group">
                <div className="single-message single-message-right">
                    <p className="message">Hello World</p>
                    <p className="message-metadata message-metadata-right">23:34</p>
                </div>
                <div className="single-message single-message-right">
                    <p className="message">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries</p>
                    <p className="message-metadata message-metadata-right">23:34</p>
                </div>
                <div className="single-message single-message-right">
                    <p className="message">It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here'.</p>
                    <p className="message-metadata message-metadata-right">23:35</p>
                </div>
            </div>
            <div className="message-avatar-container">
                <img alt="contact's avatar" className="contact-avatar-img" src={'/assets/avatar_1.png'} />
            </div>
        </div>

        <div className="date-separator">
            <p>- Monday 23.09.2022 -</p>
        </div>

        <div className="message-group-wrapper left">
            <div className="message-avatar-container">
                <img alt="contact's avatar" className="contact-avatar-img" src={'/assets/avatar_1.png'} />
            </div>
            <div className="message-group">
                <div className="single-message single-message-left">
                    <p className="message single-message-left">Hello World</p>
                    <p className="message-metadata message-metadata-left">23:34</p>
                </div>
                <div className="single-message single-message-left">
                    <p className="message">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries</p>
                    <p className="message-metadata message-metadata-left">23:34</p>
                </div>
                <div className="single-message single-message-left">
                    <p className="message">It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here'.</p>
                    <p className="message-metadata message-metadata-left">23:35</p>
                </div>
            </div>
        </div>

        <div className="date-separator">
            <p>- Monday 23.09.2022 -</p>
        </div>

        <div className="message-group-wrapper left">
            <div className="message-avatar-container">
                <img alt="contact's avatar" className="contact-avatar-img" src={'/assets/avatar_1.png'} />
            </div>
            <div className="message-group">
                <div className="single-message single-message-left">
                    <p className="message single-message-left">Hello World</p>
                    <p className="message-metadata message-metadata-left">23:34</p>
                </div>
                <div className="single-message single-message-left">
                    <p className="message">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries</p>
                    <p className="message-metadata message-metadata-left">23:34</p>
                </div>
                <div className="single-message single-message-left">
                    <p className="message">It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here'.</p>
                    <p className="message-metadata message-metadata-left">23:35</p>
                </div>
            </div>
        </div>

    </>
}