"use client";
import { useState, SyntheticEvent } from "react";
import type { User } from "@prisma/client";
import { useRouter } from "next/navigation";
import axios from "axios";

type Profile = {
    id: number;
    email: string;
    password: string;
    name: string;
    nim: string;
    picture: string;
    userId: number;
};

const UpdateProfile = ({
    users, 
    profile, 
    }: {
    users : User[];
    profile: Profile
    }) => {
    const [email, setEmail] = useState(profile.email);
    const [password, setPassword] = useState(profile.password);
    const [name, setName] = useState(profile.name);
    const [nim, setNim] = useState(profile.nim); 
    const [picture, setPicture] = useState(profile.picture); 
    const [user, setUser] = useState(profile.userId); 

    const [isOpen, setIsOpen] = useState(false);
    const router = useRouter();

    const handleUpdate = async (e: SyntheticEvent) => {
        e.preventDefault();
        await axios.patch(`/api/admin/${profile.id}`, {
            email: email,
            password: password,
            name: name,
            nim: nim,
            picture: picture,
            userId: Number(user)
        })
        router.refresh();
        setIsOpen(false);
    }

    const handleModal = () => {
        setIsOpen(!isOpen);
    }

    return (
    <div>
        <button className="btn btn-info btn-sm" onClick={handleModal}>
            Edit
        </button>

        <div className={isOpen ? 'modal modal-open' : 'modal'}>
            <div className="modal-box">
                <h3 className="font-bold text-lg">Update Profile</h3>
                <form onSubmit={handleUpdate}>
                    <div className="form-control w-full">
                        <label className="label font-bold">Email</label>
                        <input 
                        type="text"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        className="input input-bordered" 
                        placeholder="Email"/>
                    </div>
                    <div className="form-control w-full">
                        <label className="label font-bold">Password</label>
                        <input 
                        type="password" 
                        onChange={(e) => setPassword(e.target.value)}
                        className="input input-bordered" 
                        placeholder="Password"/>
                    </div>
                    <div className="form-control w-full">
                        <label className="label font-bold">Full Name</label>
                        <input 
                        type="text" 
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        className="input input-bordered" 
                        placeholder="Full Name"/>
                    </div>
                    <div className="form-control w-full">
                        <label className="label font-bold">NIM</label>
                        <input 
                        type="text" 
                        value={nim}
                        onChange={(e) => setNim(e.target.value)}
                        className="input input-bordered" 
                        placeholder="NIM"/>
                    </div>
                    <div className="form-control w-full">
                        <label className="label font-bold">Picture</label>
                        <input 
                        type="file" 
                        value={picture}
                        onChange={(e) => setPicture(e.target.value)}
                        className="input input-bordered" 
                        placeholder="Picture"/>
                    </div>
                    <div className="form-control w-full">
                        <label className="label font-bold">Role</label>
                        <select 
                        value={user}
                        onChange={(e) => setUser(Number(e.target.value))}
                        className="select select-bordered">
                        {users.map((user) => (
                            <option value={user.id} key={user.id}>{user.role}</option>
                        ))}
                        </select>
                    </div>
                    <div className="modal-action">
                        <button type="button" className="btn" onClick={handleModal}>
                            Close
                        </button>
                        <button type="submit" className="btn btn-primary">
                            Update
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
  )
}

export default UpdateProfile